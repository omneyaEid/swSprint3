package com.example.demo.service;

import com.example.demo.dao.CURD;
import com.example.demo.dao.NotificationDataAccessService;
import com.example.demo.dao.PlaceHolder;
import com.example.demo.model.NotificationTemplat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    private final CURD curd;

    private final NotificationDataAccessService no;
    @Autowired
    public NotificationService(@Qualifier("database") CURD curd, NotificationDataAccessService no) {
        this.curd = curd;
        this.no = no;
    }

    public int Create(NotificationTemplat notification)
    {
        return curd.Create(notification);
    }
    public List<NotificationTemplat>Read()
    {
        return curd.Read();
    }
    public Optional<NotificationTemplat> getNotificationById(int id)
    {
        return curd.selectNotificationById(id);
    }
    public int Delete(int id)
    {
        return curd.Delete(id);
    }
    public int Update(int id,NotificationTemplat newNotification)
    {
        return curd.Update(id,newNotification);
    }
    public int UpdateHolder(int id, PlaceHolder hold){return no.Update2(id,hold);}

}
