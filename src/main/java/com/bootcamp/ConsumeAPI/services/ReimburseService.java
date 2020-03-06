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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String date = LocalDate.now().format(formatter);

//        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.);

        DecimalFormat cursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        cursIndonesia.setDecimalFormatSymbols(formatRp);
                String total = cursIndonesia.format(reimburse.getTotal());

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
                    if (optionalEmployee.get().getRole().equalsIgnoreCase("Manager")) {
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

                        sendMail(reimburse.getEmployee().getName(),date,reimburse.getPeriod(),"APPROVED",total,reimburse.getEmployee().getEmail());
                    }
                } else if (updateStatusDto.getStatus().equalsIgnoreCase("reject")) {
                    if (optionalEmployee.get().getRole().equalsIgnoreCase("Manager")) {
                        status.setId(3);
                        reimburse.setCurrentStatus(status);
                        reimburse.setNotes(updateStatusDto.getNotes());
                        reimburseRepository.save(reimburse);
                    } else if (optionalEmployee.get().getRole().equalsIgnoreCase("admin")) {
                        status.setId(4);
                        reimburse.setCurrentStatus(status);
                        reimburse.setNotes(updateStatusDto.getNotes());
                        reimburseRepository.save(reimburse);

                        sendMail(reimburse.getEmployee().getName(),date,reimburse.getPeriod(),"REJECTED",total,reimburse.getEmployee().getEmail());

                    }
                }
            }

            history.setStatus(status);
            historyService.save(history);

        }
        return reimburse;
    }

    private void sendMail(String name, String date, String period, String status, String total, String email) throws IOException, MessagingException {

        String address="http://localhost:8083/login";

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
        messageBodyPart.setContent("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                        "<html xmlns:v=\"urn:schemas-microsoft-com:vml\">\n" +
                        "\n" +
                        "<head>\n" +
                        "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0;\" />\n" +
                        "    <!--[if !mso]--><!-- -->\n" +
                        "    <link href='https://fonts.googleapis.com/css?family=Work+Sans:300,400,500,600,700' rel=\"stylesheet\">\n" +
                        "    <link href='https://fonts.googleapis.com/css?family=Quicksand:300,400,700' rel=\"stylesheet\">\n" +
                        "    <!-- <![endif]-->\n" +
                        "\n" +
                        "    <title>Material Design for Bootstrap</title>\n" +
                        "\n" +
                        "    <style type=\"text/css\">\n" +
                        "        body {\n" +
                        "            width: 100%;\n" +
                        "            background-color: #ffffff;\n" +
                        "            margin: 0;\n" +
                        "            padding: 0;\n" +
                        "            -webkit-font-smoothing: antialiased;\n" +
                        "            mso-margin-top-alt: 0px;\n" +
                        "            mso-margin-bottom-alt: 0px;\n" +
                        "            mso-padding-alt: 0px 0px 0px 0px;\n" +
                        "        }\n" +
                        "        \n" +
                        "        p,\n" +
                        "        h1,\n" +
                        "        h2,\n" +
                        "        h3,\n" +
                        "        h4 {\n" +
                        "            margin-top: 0;\n" +
                        "            margin-bottom: 0;\n" +
                        "            padding-top: 0;\n" +
                        "            padding-bottom: 0;\n" +
                        "        }\n" +
                        "        \n" +
                        "        span.preheader {\n" +
                        "            display: none;\n" +
                        "            font-size: 1px;\n" +
                        "        }\n" +
                        "        \n" +
                        "        html {\n" +
                        "            width: 100%;\n" +
                        "        }\n" +
                        "        \n" +
                        "        table {\n" +
                        "            font-size: 14px;\n" +
                        "            border: 0;\n" +
                        "        }\n" +
                        "        /* ----------- responsivity ----------- */\n" +
                        "        \n" +
                        "        @media only screen and (max-width: 640px) {\n" +
                        "            /*------ top header ------ */\n" +
                        "            .main-header {\n" +
                        "                font-size: 20px !important;\n" +
                        "            }\n" +
                        "            .main-section-header {\n" +
                        "                font-size: 28px !important;\n" +
                        "            }\n" +
                        "            .show {\n" +
                        "                display: block !important;\n" +
                        "            }\n" +
                        "            .hide {\n" +
                        "                display: none !important;\n" +
                        "            }\n" +
                        "            .align-center {\n" +
                        "                text-align: center !important;\n" +
                        "            }\n" +
                        "            .no-bg {\n" +
                        "                background: none !important;\n" +
                        "            }\n" +
                        "            /*----- main image -------*/\n" +
                        "            .main-image img {\n" +
                        "                width: 440px !important;\n" +
                        "                height: auto !important;\n" +
                        "            }\n" +
                        "            /* ====== divider ====== */\n" +
                        "            .divider img {\n" +
                        "                width: 440px !important;\n" +
                        "            }\n" +
                        "            /*-------- container --------*/\n" +
                        "            .container590 {\n" +
                        "                width: 440px !important;\n" +
                        "            }\n" +
                        "            .container580 {\n" +
                        "                width: 400px !important;\n" +
                        "            }\n" +
                        "            .main-button {\n" +
                        "                width: 220px !important;\n" +
                        "            }\n" +
                        "            /*-------- secions ----------*/\n" +
                        "            .section-img img {\n" +
                        "                width: 320px !important;\n" +
                        "                height: auto !important;\n" +
                        "            }\n" +
                        "            .team-img img {\n" +
                        "                width: 100% !important;\n" +
                        "                height: auto !important;\n" +
                        "            }\n" +
                        "        }\n" +
                        "        \n" +
                        "        @media only screen and (max-width: 479px) {\n" +
                        "            /*------ top header ------ */\n" +
                        "            .main-header {\n" +
                        "                font-size: 18px !important;\n" +
                        "            }\n" +
                        "            .main-section-header {\n" +
                        "                font-size: 26px !important;\n" +
                        "            }\n" +
                        "            /* ====== divider ====== */\n" +
                        "            .divider img {\n" +
                        "                width: 280px !important;\n" +
                        "            }\n" +
                        "            /*-------- container --------*/\n" +
                        "            .container590 {\n" +
                        "                width: 280px !important;\n" +
                        "            }\n" +
                        "            .container590 {\n" +
                        "                width: 280px !important;\n" +
                        "            }\n" +
                        "            .container580 {\n" +
                        "                width: 260px !important;\n" +
                        "            }\n" +
                        "            /*-------- secions ----------*/\n" +
                        "            .section-img img {\n" +
                        "                width: 280px !important;\n" +
                        "                height: auto !important;\n" +
                        "            }\n" +
                        "        }\n" +
                        "    </style>\n" +
                        "    <!-- [if gte mso 9]><style type=”text/css”>\n" +
                        "        body {\n" +
                        "        font-family: arial, sans-serif!important;\n" +
                        "        }\n" +
                        "        </style>\n" +
                        "    <![endif]-->\n" +
                        "</head>\n" +
                        "\n" +
                        "\n" +
                        "<body class=\"respond\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n" +
                        "    <!-- pre-header -->\n" +
                        "    <table style=\"display:none!important;\">\n" +
                        "        <tr>\n" +
                        "            <td>\n" +
                        "                <div style=\"overflow:hidden;display:none;font-size:1px;color:#ffffff;line-height:1px;font-family:Arial;maxheight:0px;max-width:0px;opacity:0;\">\n" +
                        "                    Pre-header for the newsletter template\n" +
                        "                </div>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "    </table>\n" +
                        "    <!-- pre-header end -->\n" +
                        "    <!-- header -->\n" +
                        "    <table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"ffffff\">\n" +
                        "\n" +
                        "        <tr>\n" +
                        "            <td align=\"center\">\n" +
                        "                <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590\">\n" +
                        "\n" +
                        "                    <tr>\n" +
                        "                        <td height=\"25\" style=\"font-size: 25px; line-height: 25px;\">&nbsp;</td>\n" +
                        "                    </tr>\n" +
                        "\n" +
                        "                    <tr>\n" +
                        "                        <td align=\"center\">\n" +
                        "\n" +
                        "                            <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590\">\n" +
                        "\n" +
                        "                                <tr>\n" +
                        "                                     <td align=\"center\" style=\"width: 325px; height: 117px;\">\n" +
                        "                                        <a href=\"\" style=\"display: block; border-style: none !important; border: 0 !important;\"><img  style=\"width: 325px; height: 117px;\" src=\"https://www.pacific.edu/images/student-life/ASuop/Reimbursement%20Form.png\" alt=\"\" /></a>\n" +
                        "                                    </td>\n" +
                        "                                </tr>\n" +
                        "                            </table>\n" +
                        "                        </td>\n" +
                        "                    </tr>\n" +
                        "\n" +
                        "                    <tr>\n" +
                        "                        <td height=\"25\" style=\"font-size: 25px; line-height: 25px;\">&nbsp;</td>  \n" +
                        "                    </tr>\n" +
                        "\n" +
                        "                </table>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "    </table>\n" +
                        "    <!-- end header -->\n" +
                        "\n" +
                        "    <!-- big image section -->\n" +
                        "    <table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"ffffff\" class=\"bg_color\">\n" +
                        "\n" +
                        "        <tr>\n" +
                        "            <td align=\"center\">\n" +
                        "                <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590\">\n" +
                        "                   \n" +
                        "                    \n" +
                        "                    <tr>\n" +
                        "                        <td align=\"center\" style=\"color: #343434; font-size: 24px; font-family: Quicksand, Calibri, sans-serif; font-weight:700;letter-spacing: 3px; line-height: 35px;\" class=\"main-header\">\n" +
                        "\n" +
                        "\n" +
                        "                            <div style=\"line-height: 35px\">\n" +
                        "\n" +
                        "                                Hello,  <span style=\"color: #5caad2;\">"+name+"</span>\n" +
                        "\n" +
                        "                            </div>\n" +
                        "                        </td>\n" +
                        "                    </tr>\n" +
                        "\n" +
                        "                    <tr>\n" +
                        "                        <td height=\"10\" style=\"font-size: 10px; line-height: 10px;\">&nbsp;</td>\n" +
                        "                    </tr>\n" +
                        "\n" +
                        "                    <tr>\n" +
                        "                        <td align=\"center\">\n" +
                        "                            <table border=\"0\" width=\"40\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"eeeeee\">\n" +
                        "                                <tr>\n" +
                        "                                    <td height=\"2\" style=\"font-size: 2px; line-height: 2px;\">&nbsp;</td>\n" +
                        "                                </tr>\n" +
                        "                            </table>\n" +
                        "                        </td>\n" +
                        "                    </tr>\n" +
                        "\n" +
                        "                    <tr>\n" +
                        "                        <td height=\"20\" style=\"font-size: 20px; line-height: 20px;\">&nbsp;</td>\n" +
                        "                    </tr>\n" +
                        "\n" +
                        "                    <tr>\n" +
                        "                        <td align=\"center\">\n" +
                        "                            <table border=\"0\" width=\"400\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590\">\n" +
                        "                                <tr>\n" +
                        "                                    <td align=\"center\" style=\"color: #888888; font-size: 16px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 24px;\">\n" +
                        "\n" +
                        "\n" +
                        "                                        <div style=\"line-height: 24px\">\n" +
                        "\t\t\t\t\t\t\t\t\t\tPeriod :                                   \n" +
                        "\t\t\t\t\t\t\t\t\t\t</div>\n" +
                        "                                    </td>\n" +
                        "                                    <td align=\"center\" style=\"color: #888888; font-size: 16px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 24px;\">\n" +
                        "\n" +
                        "\n" +
                        "                                        <div style=\"line-height: 24px\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t                                 \n" +period+
                        "\t\t\t\t\t\t\t\t\t\t</div>\n" +
                        "                                    </td>\n" +
                        "                                </tr>\n" +
                        "                                   <tr>\n" +
                        "                                    <td align=\"center\" style=\"color: #888888; font-size: 16px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 24px;\">\n" +
                        "\n" +
                        "\n" +
                        "                                        <div style=\"line-height: 24px\">\n" +
                        "\t\t\t\t\t\t\t\t\t\tDate :                                   \n" +
                        "\t\t\t\t\t\t\t\t\t\t</div>\n" +
                        "                                    </td>\n" +
                        "                                    <td align=\"center\" style=\"color: #888888; font-size: 16px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 24px;\">\n" +
                        "\n" +
                        "\n" +
                        "                                        <div style=\"line-height: 24px\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t                                 \n" +date+
                        "\t\t\t\t\t\t\t\t\t\t</div>\n" +
                        "                                    </td>\n" +
                        "                                </tr>\n" +
                        "                                   <tr>\n" +
                        "                                    <td align=\"center\" style=\"color: #888888; font-size: 16px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 24px;\">\n" +
                        "\n" +
                        "\n" +
                        "                                        <div style=\"line-height: 24px\">\n" +
                        "\t\t\t\t\t\t\t\t\t\tStatus :                                   \n" +
                        "\t\t\t\t\t\t\t\t\t\t</div>\n" +
                        "                                    </td>\n" +
                        "                                    <td align=\"center\" style=\"color: #888888; font-size: 16px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 24px;\">\n" +
                        "\n" +
                        "\n" +
                        "                                        <div style=\"line-height: 24px\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t                                  \n" +status+
                        "\t\t\t\t\t\t\t\t\t\t</div>\n" +
                        "                                    </td>\n" +
                        "                                </tr>\n" +
                        "                                   <tr>\n" +
                        "                                    <td align=\"center\" style=\"color: #888888; font-size: 16px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 24px;\">\n" +
                        "\n" +
                        "\n" +
                        "                                        <div style=\"line-height: 24px\">\n" +
                        "\t\t\t\t\t\t\t\t\t\tTotal :                                   \n" +
                        "\t\t\t\t\t\t\t\t\t\t</div>\n" +
                        "                                    </td>\n" +
                        "                                    <td align=\"center\" style=\"color: #888888; font-size: 16px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 24px;\">\n" +
                        "\n" +
                        "\n" +
                        "                                        <div style=\"line-height: 24px\">\n" +
                        "\t\t\t\t\t\t\t\t\t\tRp                                   \n" +total+
                        "\t\t\t\t\t\t\t\t\t\t</div>\n" +
                        "                                    </td>\n" +
                        "                                </tr>\n" +
                        "                            </table>\n" +
                        "                        </td>\n" +
                        "                    </tr>\n" +
                        "\n" +
                        "                    <tr>\n" +
                        "                        <td height=\"25\" style=\"font-size: 25px; line-height: 25px;\">&nbsp;</td>\n" +
                        "                    </tr>\n" +
                        "\n" +
                        "                    <tr>\n" +
                        "                        <td align=\"center\">\n" +
                        "                            <table border=\"0\" align=\"center\" width=\"160\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"5caad2\" style=\"\">\n" +
                        "\n" +
                        "                                <tr>\n" +
                        "                                    <td height=\"10\" style=\"font-size: 10px; line-height: 10px;\">&nbsp;</td>\n" +
                        "                                </tr>\n" +
                        "\n" +
                        "                                <tr>\n" +
                        "                                    <td align=\"center\" style=\"color: #ffffff; font-size: 14px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 26px;\">\n" +
                        "\n" +
                        "\n" +
                        "                                        <div style=\"line-height: 26px;\">\n" +
                        "                                            <a href=\"" + address + "\" style=\"color: #ffffff; text-decoration: none;\">CHECK</a>\n" +
                        "                                        </div>\n" +
                        "                                    </td>\n" +
                        "                                </tr>\n" +
                        "\n" +
                        "                                <tr>\n" +
                        "                                    <td height=\"10\" style=\"font-size: 10px; line-height: 10px;\">&nbsp;</td>\n" +
                        "                                </tr>\n" +
                        "\n" +
                        "                            </table>\n" +
                        "                        </td>\n" +
                        "                    </tr>\n" +
                        "\n" +
                        "\n" +
                        "                </table>\n" +
                        "\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "\n" +
                        "    </table>\n" +
                        "    <!-- end section -->\n" +
                        "\n" +
                        "    <!-- contact section -->\n" +
                        "    <table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"ffffff\" class=\"bg_color\">\n" +
                        "\n" +
                        "        <tr class=\"hide\">\n" +
                        "            <td height=\"25\" style=\"font-size: 25px; line-height: 25px;\">&nbsp;</td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td height=\"40\" style=\"font-size: 40px; line-height: 40px;\">&nbsp;</td>\n" +
                        "        </tr>\n" +
                        "\n" +
                        "        <tr>\n" +
                        "            <td height=\"60\" style=\"border-top: 1px solid #e0e0e0;font-size: 60px; line-height: 60px;\">&nbsp;</td>\n" +
                        "        </tr>\n" +
                        "\n" +
                        "        <tr>\n" +
                        "            <td align=\"center\">\n" +
                        "                <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590 bg_color\">\n" +
                        "\n" +
                        "                    <tr>\n" +
                        "                        <td>\n" +
                        "                            <table border=\"0\" width=\"300\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\" class=\"container590\">\n" +
                        "\n" +
                        "                                <tr>\n" +
                        "                                    <!-- logo -->\n" +
                        "                                    <td align=\"left\">\n" +
                        "                                        <a href=\"\" style=\"display: block; border-style: none !important; border: 0 !important;\"><img width=\"80\" border=\"0\" style=\"display: block; width: 80px;\" src=\"https://www.pacific.edu/images/student-life/ASuop/Reimbursement%20Form.png\" alt=\"\" /></a>\n" +
                        "                                    </td>\n" +
                        "                                </tr>\n" +
                        "\n" +
                        "                                <tr>\n" +
                        "                                    <td height=\"25\" style=\"font-size: 25px; line-height: 25px;\">&nbsp;</td>\n" +
                        "                                </tr>\n" +
                        "\n" +
                        "                                <tr>\n" +
                        "                                    <td align=\"left\" style=\"color: #888888; font-size: 14px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 23px;\" class=\"text_color\">\n" +
                        "                                        <div style=\"color: #333333; font-size: 14px; font-family: 'Work Sans', Calibri, sans-serif; font-weight: 600; mso-line-height-rule: exactly; line-height: 23px;\">\n" +
                        "\n" +
                        "                                           Support by: <br/> <a href=\"mailto:\" style=\"color: #888888; font-size: 14px; font-family: 'Hind Siliguri', Calibri, Sans-serif; font-weight: 400;\">Reimbursement Wik Wik Team</a>\n" +
                        "\n" +
                        "                                        </div>\n" +
                        "                                    </td>\n" +
                        "                                </tr>\n" +
                        "\n" +
                        "                            </table>\n" +
                        "\n" +

                        "    <!-- footer ====== -->\n" +
                        "    <table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"f4f4f4\">\n" +
                        "\n" +
                        "        <tr>\n" +
                        "            <td height=\"25\" style=\"font-size: 25px; line-height: 25px;\">&nbsp;</td>\n" +
                        "        </tr>\n" +
                        "\n" +
                        "        <tr>\n" +
                        "            <td align=\"center\">\n" +
                        "\n" +
                        "                <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590\">\n" +
                        "\n" +
                        "                    <tr>\n" +
                        "                        <td>\n" +
                        "                            <table border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\" class=\"container590\">\n" +
                        "                                <tr>\n" +
                        "                                    <td align=\"left\" style=\"color: #aaaaaa; font-size: 14px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 24px;\">\n" +
                        "                                        <div style=\"line-height: 24px;\">\n" +
                        "\n" +
                        "                                            <span style=\"color: #333333;\">Material Design for Reimbursement</span>\n" +
                        "\n" +
                        "                                        </div>\n" +
                        "                                    </td>\n" +
                        "                                </tr>\n" +
                        "                            </table>\n" +
                        "\n" +
                        "                            <table border=\"0\" align=\"left\" width=\"5\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\" class=\"container590\">\n" +
                        "                                <tr>\n" +
                        "                                    <td height=\"20\" width=\"5\" style=\"font-size: 20px; line-height: 20px;\">&nbsp;</td>\n" +
                        "                                </tr>\n" +
                        "                            </table>\n" +
                        "\n" +
                        "                            <table border=\"0\" align=\"right\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\" class=\"container590\">\n" +
                        "\n" +
                        "                                <tr>\n" +
                        "                                    <td align=\"center\">\n" +
                        "                                        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                        "                                            <tr>\n" +
                        "                                                <td align=\"center\">\n" +
                        "                                                    <a style=\"font-size: 14px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 24px;color: #5caad2; text-decoration: none;font-weight:bold;\" href=\"{{UnsubscribeURL}}\">WIK WIK TEAM</a>\n" +
                        "                                                </td>\n" +
                        "                                            </tr>\n" +
                        "                                        </table>\n" +
                        "                                    </td>\n" +
                        "                                </tr>\n" +
                        "\n" +
                        "                            </table>\n" +
                        "                        </td>\n" +
                        "                    </tr>\n" +
                        "\n" +
                        "                </table>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "\n" +
                        "        <tr>\n" +
                        "            <td height=\"25\" style=\"font-size: 25px; line-height: 25px;\">&nbsp;</td>\n" +
                        "        </tr>\n" +
                        "\n" +
                        "    </table>\n" +
                        "    <!-- end footer ====== -->\n" +
                        "\n" +
                        "</body>\n" +
                        "\n" +
                        "</html>",
                "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        message.setContent(multipart);
        Transport.send(message);


    }

}
