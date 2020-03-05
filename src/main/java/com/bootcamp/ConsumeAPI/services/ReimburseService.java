package com.bootcamp.ConsumeAPI.services;


import com.bootcamp.ConsumeAPI.entities.*;
import com.bootcamp.ConsumeAPI.repositories.ReimburseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class ReimburseService {

    @Autowired
    private ReimburseRepository reimburseRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private HistoryService historyService;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.properties.mail.smtp.port}")
    private String port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String starttlsEnable;

    public Reimburse save(Reimburse reimburse) {
        Employee employee = new Employee(reimburse.getEmployee().getId());
        Optional<Reimburse> optionalReimburse = reimburseRepository.findById(reimburse.getId());
        if (!optionalReimburse.isPresent()) {
            reimburseRepository.save(reimburse);
        } else {
            Reimburse reimburse1 = optionalReimburse.get();
            reimburse1.setCurrentStatus(reimburse.getCurrentStatus());
            reimburse1.setEmployee(employee);
            reimburse1.setEndDate(reimburse.getEndDate());
            reimburse1.setNotes(reimburse.getNotes());
            reimburse1.setTotal(reimburse.getTotal());

            reimburseRepository.save(reimburse1);
        }
        return reimburse;
    }

    public Optional<Reimburse> findById(String id) {
        return reimburseRepository.findById(id);
    }

    public List<Reimburse> getByStatus(String statusId) {
        Optional<Employee> optionalReimburse = employeeService.getById(statusId);
        List<Reimburse> reimburseList = new ArrayList<>();

        if (optionalReimburse.get().getRole().equalsIgnoreCase("Trainer") || optionalReimburse.get().getRole().equalsIgnoreCase("manager")) {
            reimburseList = reimburseRepository.findByCurrentStatus_Id(1);
        } else if (optionalReimburse.get().getRole().equalsIgnoreCase("Admin")) {
            reimburseList = reimburseRepository.findByCurrentStatus_Id(2);
        }
        return reimburseList;
    }

    public Reimburse updateStatus(UpdateStatusDto updateStatusDto) throws IOException, MessagingException {
        Reimburse reimburse = new Reimburse();
        Optional<Reimburse> optionalReimburse = reimburseRepository.findById(updateStatusDto.getId());

        Optional<Employee> optionalEmployee = employeeService.getById(updateStatusDto.getEmployeeId());

        if (optionalReimburse.isPresent()) {
            reimburse = optionalReimburse.get();

            Status status = new Status();

            History history = new History();
            history.setId(0);
            history.setNotes(updateStatusDto.getNotes());
            history.setReimburse(reimburse);
            history.setApprovalBy(optionalEmployee.get());

            if (optionalEmployee.isPresent()) {
                if (updateStatusDto.getStatus().equalsIgnoreCase("approved")) {
                    if (optionalEmployee.get().getRole().equalsIgnoreCase("Trainer")) {
                        status.setId(5);
                        reimburse.setCurrentStatus(status);
                        reimburse.setNotes(updateStatusDto.getNotes());
                        reimburseRepository.save(reimburse);

                        history.setStatus(status);
                        historyService.save(history);

                        status.setId(2);
                        reimburse.setCurrentStatus(status);
                        reimburseRepository.save(reimburse);

                    } else if (optionalEmployee.get().getRole().equalsIgnoreCase("admin")) {
                        status.setId(6);
                        reimburse.setCurrentStatus(status);
                        reimburse.setNotes(updateStatusDto.getNotes());
                        reimburseRepository.save(reimburse);

                        sendMail(reimburse.getEmployee().getName(),LocalDate.now(),reimburse.getPeriod(),reimburse.getCurrentStatus().getName(),reimburse.getTotal(),reimburse.getEmployee().getEmail());
                    }
                } else if (updateStatusDto.getStatus().equalsIgnoreCase("reject")) {
                    if (optionalEmployee.get().getRole().equalsIgnoreCase("trainer")) {
                        status.setId(3);
                        reimburse.setCurrentStatus(status);
                        reimburse.setNotes(updateStatusDto.getNotes());
                        reimburseRepository.save(reimburse);
                    } else if (optionalEmployee.get().getRole().equalsIgnoreCase("admin")) {
                        status.setId(4);
                        reimburse.setCurrentStatus(status);
                        reimburse.setNotes(updateStatusDto.getNotes());
                        reimburseRepository.save(reimburse);

                        sendMail(reimburse.getEmployee().getName(),LocalDate.now(),reimburse.getPeriod(),reimburse.getCurrentStatus().getName(),reimburse.getTotal(),reimburse.getEmployee().getEmail());

                    }
                }
            }

            history.setStatus(status);
            historyService.save(history);

        }
        return reimburse;
    }

    private void sendMail(String name, LocalDate date, String period, String status, int total, String email) throws IOException, MessagingException {

        Properties props = new Properties();
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", starttlsEnable);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("test.spring.boot.email@gmail.com", false));

        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject("Notification Reimburse");
        message.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("sudah terkirim ",
                "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        message.setContent(multipart);
        Transport.send(message);


    }

}
