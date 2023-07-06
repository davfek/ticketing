package com.example.ticketing.ticket;

public enum TicketStatus {
    CREATED,IN_PROGRESS,BLOCKED,RESOLVED;

    TicketStatus() {
    }
    public static TicketStatus defineStatusFromString(String s){
        String formattedString = s.toLowerCase().replaceAll("[^a-zA-Z]", "").trim();

        switch (formattedString){
            case "inprogress":
                return TicketStatus.IN_PROGRESS;
            case "blocked":
                return TicketStatus.BLOCKED;
            case "resolved":
                return TicketStatus.RESOLVED;
            default:
                return TicketStatus.CREATED;
        }
    }
}
