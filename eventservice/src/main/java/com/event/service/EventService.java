package com.event.service;

import com.event.entity.Event;
import com.event.entity.Guest;
import com.event.entity.Trash;
import com.event.model.UserModel;
import com.event.repository.EventRepository;
import com.event.repository.TrashRepository;
import com.event.util.DateUtil;
import jakarta.transaction.Transactional;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.UnsupportedEncodingException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
    private Logger logger = LoggerFactory.getLogger(EmailService.class);


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


    @Transactional
    public List<Event> addAllTasks(List<Event> events, String token) {
        String userEmail = userServiceClient.extractEmailFromToken(token);
        List<Event> savedEvents = new ArrayList<>();
        for (Event event : events) {
            event.setEmail(userEmail);

            Long dayInMillis = event.getDay();
            LocalDateTime dateTime = DateUtil.fromMillis(dayInMillis);
            if (event.getGuests() != null) {
                Set<Guest> managedGuests = new HashSet<>();
                for (Guest guest : event.getGuests()) {
                    Guest managedGuest = new Guest();
                    managedGuest.setEmail(guest.getEmail());  // Set guest email
                    managedGuest.setEventId(event.getId());
                    managedGuests.add(managedGuest);
                }
                event.setGuests(managedGuests);
            }

            Event savedEvent = eventRepository.save(event);
            savedEvents.add(savedEvent);


            if (savedEvent.getGuests() != null && !savedEvent.getGuests().isEmpty() && !savedEvent.isEmailSent())  {
                String subject = "You're invited: " + savedEvent.getTitle() ;
                for (Guest guest : savedEvent.getGuests()) {
                    String htmlContent = null;
                    try {
                        htmlContent = generateEmailHtmlContent(savedEvent, guest);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
//            String htmlContent = generateEmailHtmlContent(savedEvent,guest);

                    // Send email to all guests
                    emailService.sendEmailWithHtmlToGuests(savedEvent.getGuests(), subject, htmlContent, userEmail);
                }
                savedEvent.setEmailSent(true);
                eventRepository.save(savedEvent); //
            }
        }
        // Return all saved events
        return savedEvents;
        }

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
               // Set permission ID
                managedGuests.add(managedGuest); // Add to managed guests set
            }
            event.setGuests(managedGuests); // Assign the managed guests to the event
        }
        Event savedEvent = eventRepository.save(event);

        // Send HTML emails to all guests
        if (savedEvent.getGuests() != null && !savedEvent.getGuests().isEmpty()) {
            String subject = "You're invited: " + savedEvent.getTitle();
            for (Guest guest : savedEvent.getGuests()) {
                String htmlContent = null;
                try {
                    htmlContent = generateEmailHtmlContent(savedEvent, guest);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }



                emailService.sendEmailWithHtmlToGuests(savedEvent.getGuests(), subject, htmlContent, userEmail);
            }
        }
        return savedEvent;


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


        Trash trash = new Trash();
        trash.setId(event.getId());
        trash.setTitle(event.getTitle());
        trash.setDescription(event.getDescription());
        trash.setLabel(event.getLabel());
        trash.setDeletedAt(LocalDateTime.now());
        trash.setDay(event.getDay());
        trash.setEmail(event.getEmail());

        trashRepository.save(trash);

        eventRepository.delete(event);
    }


    public void restoreFromTrash(Long trashId) {
        Trash trash = trashRepository.findById(trashId)
                .orElseThrow(() -> new RuntimeException("Trash entry not found"));

        trashRepository.delete(trash);
    }


    private String generateEmailHtmlContent(Event event, Guest guest) throws UnsupportedEncodingException {
        String htmlTemplate = loadHtmlTemplate();


        String title =  event.getTitle()  != null ? event.getTitle() : "No Title";

        String formattedDate = "No Date"; // Default value
        Long dayInMillis = event.getDay(); // Assuming 'day' is of type Long representing milliseconds

        if (dayInMillis != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(dayInMillis),
                    ZoneId.systemDefault()
            );

            formattedDate = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        String description = event.getDescription() != null ? event.getDescription() : "No Description";


        String htmlContent = htmlTemplate
                .replace("{{eventTitle}}", title)
                .replace("{{eventDate}}", formattedDate)
                .replace("{{eventDescription}}", description);

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
                "  <p><strong>Description:</strong> {{eventDescription}}</p>" +

                "</body>" +
                "</html>";
    }

    public List<Trash> getTrashedEventsByEmail(String token) {
        String email;
        email = userServiceClient.extractEmailFromToken(token);

        return trashRepository.findByEmail(email);
    }

}
