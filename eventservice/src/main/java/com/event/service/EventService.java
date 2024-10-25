package com.event.service;

import com.event.entity.Event;
import com.event.entity.Guest;
import com.event.entity.Trash;
import com.event.model.UserModel;
import com.event.repository.EventRepository;
import com.event.repository.TrashRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TrashRepository trashRepository;


    public List<Event> getAll(){
        return eventRepository.findAll();
    }
    public List<Event> getByEmailId(@PathVariable String email){
        return eventRepository.findEventByEmail(email);
    }


    public List<Event> getEventByEmail(String email){
        UserModel userModel = userServiceClient.getUserByEmail(email);
        if(userModel == null){
            throw new RuntimeException("user not found");
        }
        return eventRepository.findByEmail(email);

    }
    public Event getById(Long id){
        return eventRepository.findById(id).orElseThrow(()->new RuntimeException("data not found"));
    }

    public List<Event> addAllTasks(List<Event> events, String token) {
        // Extract the user email from the token
        String userEmail = userServiceClient.extractEmailFromToken(token);

        // Save the events with their guests first
        List<Event> savedEvents = new ArrayList<>();

        // Iterate through each event
        for (Event event : events) {
            // Set the organizer's email for the event
            event.setEmail(userEmail);

            // Process guests if they are present in the event
            if (event.getGuests() != null) {
                Set<Guest> managedGuests = new HashSet<>();
                for (Guest guest : event.getGuests()) {
                    // Create a new Guest object to avoid transient instance errors
                    Guest managedGuest = new Guest();
                    managedGuest.setEmail(guest.getEmail());  // Set guest email
                    managedGuest.setGuestPermissionId(guest.getGuestPermissionId());  // Set guest permission ID
                    managedGuests.add(managedGuest);  // Add to the set of managed guests
                }
                event.setGuests(managedGuests);  // Assign the managed guests to the event
            }
            Event savedEvent = eventRepository.save(event);
            savedEvents.add(savedEvent);

            // Send HTML email invitations to guests after the event is saved
            if (savedEvent.getGuests() != null && !savedEvent.getGuests().isEmpty()) {
                String subject = "You're invited: " + savedEvent.getTitle();
                String htmlContent = generateEmailHtmlContent(savedEvent);  // Generate the HTML content for the email
                // Send email to all guests individually

                    emailService.sendEmailWithHtmlToGuests(savedEvent.getGuests(), subject, htmlContent,userEmail);

            }
        }

        // Return all saved events
        return savedEvents;

            // Save the event and its guests
        }



    // Save and return all events along with their guests


    @Transactional
    public Event addEvent(Event event, String token) {
        // Extract user email from the token
        String userEmail = userServiceClient.extractEmailFromToken(token);
        event.setEmail(userEmail); // Set the email in the event

        // Ensure guests are processed correctly
        if (event.getGuests() != null) {
            Set<Guest> managedGuests = new HashSet<>();
            for (Guest guest : event.getGuests()) {
                // Create a new Guest object to avoid transient instance errors
                Guest managedGuest = new Guest();
                managedGuest.setEmail(guest.getEmail()); // Set the name from the guest DTO
                managedGuest.setGuestPermissionId(guest.getGuestPermissionId()); // Set permission ID
                managedGuests.add(managedGuest); // Add to managed guests set
            }
            event.setGuests(managedGuests); // Assign the managed guests to the event
        }
        Event savedEvent = eventRepository.save(event);

        // Send HTML emails to all guests
        if (savedEvent.getGuests() != null && !savedEvent.getGuests().isEmpty()) {
            String subject = "You're invited: " + savedEvent.getTitle();
            String htmlContent = generateEmailHtmlContent(savedEvent);

            // Send email to all guests
            emailService.sendEmailWithHtmlToGuests(savedEvent.getGuests(), subject, htmlContent,userEmail);
        }

        return savedEvent;

        // Save the event with guests return eventRepository.save(event);
    }

    public void deleteById(Long id){
        eventRepository.deleteById(id);
    }

    public Event updateRecordById(Long id, Event record) {

        Optional<Event> eventRecord = eventRepository.findById(id);
        if(eventRecord.isPresent()){
            Event event =  eventRecord.get();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(record,event);
            eventRepository.save(event);

        }
        return record;
    }
    public List<Event> getAllEvent(String token){
        String email;
        email = userServiceClient.extractEmailFromToken(token);
        return eventRepository.findEventByEmail(email);
  //        return taskRepository.findAll();
    }

    public void moveToTrash(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Create a Trash entry
        Trash trash = new Trash();
        trash.setId(event.getId());
        trash.setTitle(event.getTitle());
        trash.setDescription(event.getDescription());
        trash.setLabel(event.getLabel());
        trash.setDeletedAt(LocalDateTime.now());
        trash.setDay(event.getDay());
        trash.setEmail(event.getEmail());

        trashRepository.save(trash);

        // Delete the event from the Event table
        eventRepository.delete(event);
    }

    // Restore event from Trash table to Event table
    public void restoreFromTrash(Long trashId) {
        Trash trash = trashRepository.findById(trashId)
                .orElseThrow(() -> new RuntimeException("Trash entry not found"));

         //Restore the event
//        Event event = new Event();
//        event.setId(trash.getId());
//        event.setTitle(trash.getTitle());
//        event.setDescription(trash.getDescription());
//        event.setLabel(trash.getLabel());
//        event.setEmail(trash.getEmail());
//
//        eventRepository.save(event);

        // Remove the entry from the Trash table
        trashRepository.delete(trash);
    }
    public Event saveEvent(Event event, String token) {
        // Extract user email from the token
        String userEmail = userServiceClient.extractEmailFromToken(token);
        event.setEmail(userEmail); // Set the email in the event

        // Ensure guests are processed correctly
        if (event.getGuests() != null) {
            Set<Guest> managedGuests = new HashSet<>();
            for (Guest guest : event.getGuests()) {
                // Create a new Guest object to avoid transient instance errors
                Guest managedGuest = new Guest();
                managedGuest.setEmail(guest.getEmail());
                managedGuest.setGuestPermissionId(guest.getGuestPermissionId());
                managedGuests.add(managedGuest);
            }
            event.setGuests(managedGuests); // Assign the managed guests to the event
        }

        // Save the event with guests
        Event savedEvent = eventRepository.save(event);

        // Send HTML emails to all guests
        if (savedEvent.getGuests() != null && !savedEvent.getGuests().isEmpty()) {
            String subject = "You're invited: " + savedEvent.getTitle();
            String htmlContent = generateEmailHtmlContent(savedEvent);

            // Send email to all guests
            emailService.sendEmailWithHtmlToGuests(savedEvent.getGuests(), subject, htmlContent,userEmail);
        }

        return savedEvent;
    }

    // Generate email HTML content (as before)
    private String generateEmailHtmlContent(Event event) {
        String htmlTemplate = loadHtmlTemplate();

        // Use Optional or null checks to safely replace null values with default strings
        String title = event.getTitle() != null ? event.getTitle() : "No Title";
        String day = event.getDay() != null ? event.getDay().toString() : "No Date";
        String description = event.getDescription() != null ? event.getDescription() : "No Description";

//        String time = event.getTime() != null ? event.getTime().toString() : "10:00 AM"; // Assuming 'event.getTime()' returns a time

        String htmlContent = htmlTemplate
                .replace("{{eventTitle}}", title)
                .replace("{{eventDate}}", day)
//                .replace("{{eventTime}}", time)
//                .replace("{{eventLocation}}", location)
                .replace("{{eventDescription}}", description)
                .replace("{{acceptLink}}", "http://example.com/accept?eventId=" + event.getId())
                .replace("{{declineLink}}", "http://example.com/decline?eventId=" + event.getId())
                .replace("{{tentativeLink}}", "http://example.com/tentative?eventId=" + event.getId());

        return htmlContent;
    }

    // Load HTML template (polished to look more like Google Calendar)
    private String loadHtmlTemplate() {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style>" +
                "  body { font-family: Arial, sans-serif; color: #333333; }" +
                "  .header { background-color: #f1f1f1; padding: 20px; text-align: center; }" +
                "  .header h1 { margin: 0; font-size: 24px; }" +
                "  .details { margin: 20px 0; }" +
                "  .details p { font-size: 16px; margin: 5px 0; }" +
                "  .action-buttons { margin-top: 20px; }" +
                "  .button { display: inline-block; padding: 10px 20px; margin: 0 10px; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 5px; }" +
                "  .button:hover { background-color: #45a049; }" +
                "  .footer { margin-top: 20px; font-size: 12px; color: #777777; text-align: center; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='header'>" +
                "  <h1>{{eventTitle}}</h1>" +
                "</div>" +
                "<div class='details'>" +
                "  <p><strong>Date:</strong> {{eventDate}}</p>" +
                "  <p><strong>Time:</strong> {{eventTime}}</p>" +
                "  <p><strong>Location:</strong> {{eventLocation}}</p>" +
                "  <p><strong>Description:</strong> {{eventDescription}}</p>" +
                "</div>" +
                "<div class='action-buttons'>" +
                "  <a class='button' href='{{acceptLink}}'>Accept</a>" +
                "  <a class='button' href='{{declineLink}}'>Decline</a>" +
                "  <a class='button' href='{{tentativeLink}}'>Tentative</a>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    public List<Trash> getTrashedEventsByEmail(String token) {
        String email;
        email = userServiceClient.extractEmailFromToken(token);
        // Call the repository method to find trashed events by email
        return trashRepository.findByEmail(email);
    }
    public List<Event> getAllActiveEvents() {
        return eventRepository.findAll(); // Modify as needed to filter only active events if needed.
    }

}
