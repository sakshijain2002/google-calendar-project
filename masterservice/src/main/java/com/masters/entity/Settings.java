package com.masters.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "time_zone_id")
    private TimeZone timeZone;
//    @ManyToOne
//    @JoinColumn(name = "country_id")
//    private Country country;

    @ManyToOne
    @JoinColumn(name = "date_format_id")
    private DateFormat dateFormat;

    @ManyToOne
    @JoinColumn(name = "event_duration_id")
    private DefaultDuration defaultEventDuration;
    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @ManyToOne
    @JoinColumn(name="add_Invitation_id")
    private AddInvitation addInvitation;

    @ManyToOne
    @JoinColumn(name="alternative_Calendar_id")
    private AlternativeCalendar alternativeCalendar;
    @ManyToOne
    @JoinColumn(name="custom_view_Id")
    private CustomView customView;

    @ManyToOne
    @JoinColumn(name="default_duration_id")
    private DefaultDuration defaultDuration;

    @ManyToOne
    @JoinColumn(name="email_event_Privacy_id")
    private EmailEventPrivacy emailEventPrivacy;

    @ManyToOne
    @JoinColumn(name="guest_permission_id")
    private GuestPermission guestPermission;

    @ManyToOne
    @JoinColumn(name="notification_id")
    private Notification notification;

    @ManyToOne
    @JoinColumn(name="snoozed_notification_id")
    private  SnoozedNotifications snoozedNotifications;

    @ManyToOne
    @JoinColumn(name="start_Week_id")
    private StartWeek startWeek;

    @ManyToOne
    @JoinColumn(name="time_format_id")
    private TimeFormat timeFormat;

    private Boolean secondaryTimeZone;
    private Boolean showWorldClock;
    private Boolean speedyMeetings;
    private Boolean addGoogleMeet;
    private Boolean notificationSound;
    private Boolean notifyMe;
    private Boolean showWeekends;
    private Boolean showdeclinedEvents;
    private Boolean showCompletedTasks;
    private Boolean showWeekNumbers;
    private Boolean shorterEvents;
    private Boolean reduceBrightness;
    private Boolean sideDayView;
    private Boolean showEventsAutomatically;

    private Boolean enableKeyboardShortcut;



}
