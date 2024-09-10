package com.settings.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DateFormat;

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
    private Long timeZoneId;
    private Long countryId;
    private Integer  dateFormatId;
    private Long defaultEventDurationId;
    private Long languageId;
    private Long addInvitationId;
    private Long alternativeCalendarId;
    private Long customViewId;
    private Long defaultDurationId;
    private Long emailEventPrivacyId;
    private Long guestPermissionId;
    private Long notificationId;
    private Long snoozedNotificationsId;
    private Long startWeekId;
    private Long timeFormatId;
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
