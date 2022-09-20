package com.example.ChatAppVaadin1.model;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventBus;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.shared.Registration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class Storage {

    @Getter
    private Queue<ChatMessage> messages = new ConcurrentLinkedQueue<>();
    private ComponentEventBus eventBus = new ComponentEventBus(new Div());


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
