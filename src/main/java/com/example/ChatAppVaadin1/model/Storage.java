package com.example.ChatAppVaadin1.model;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventBus;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.shared.Registration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**A chat model that consists of Queue<ChatMessage> and ComponentEventBus.
 ChatMessage contains the username and the message to be sent.
 ComponentEventBus executes
 Your component class can provide an event-handling API that uses the event bus provided by the Component base class.
 The event bus supports:
 event classes that extend ComponentEvent, and
 listeners of the type ComponentEventListener<EventType>.**/


@Component
public class Storage {

    @Getter
    private final Queue<ChatMessage> messages = new ConcurrentLinkedQueue<>();
    private final ComponentEventBus eventBus = new ComponentEventBus(new Div());


    public static class ChatEvent extends ComponentEvent<Div>{
        public ChatEvent( ) {
            super(new Div(), false);
        }
    }
    @Getter
    @AllArgsConstructor
    public static class ChatMessage{
        private String name;
        private String message;
    }


    public void addRecord(String user, String message) {
        messages.add(new ChatMessage(user, message));
        eventBus.fireEvent(new ChatEvent());
    }


    public Registration attachListener(ComponentEventListener<ChatEvent> messageListener){
       return eventBus.addListener(ChatEvent.class, messageListener);
    }


    public void addRecordJoined(String user) {
        messages.add(new ChatMessage("", user));
        eventBus.fireEvent(new ChatEvent());
    }


    public int size() {
        return messages.size();
    }
}
