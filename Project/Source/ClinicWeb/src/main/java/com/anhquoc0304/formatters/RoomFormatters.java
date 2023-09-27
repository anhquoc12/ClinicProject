/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.formatters;

import com.anhquoc0304.pojo.Room;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author Admin
 */
public class RoomFormatters implements Formatter<Room>{

    @Override
    public String print(Room object, Locale locale) {
        return String.valueOf(object.getId());
    }

    @Override
    public Room parse(String text, Locale locale) throws ParseException {
        return new Room(Integer.parseInt(text));
    }
    
}
