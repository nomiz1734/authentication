package com.example.demoEmail.service.impl;

import com.example.demoEmail.dto.DataMailDTO;
import com.example.demoEmail.dto.DataOtp;
import com.example.demoEmail.dto.sdi.ClientSdi;
import com.example.demoEmail.service.ClientService;
import com.example.demoEmail.service.MailService;
import com.example.demoEmail.utils.Const;
import com.example.demoEmail.utils.DataUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private MailService mailService;

    private ConcurrentHashMap<String, DataOtp> otpStore = new ConcurrentHashMap<>();
    private static final int EXPIRY_MINUTES = 5;
    @Override
    public Boolean create(ClientSdi sdi) {

        try {
            DataMailDTO dataMail = new DataMailDTO();

            dataMail.setTo(sdi.getEmail());
            dataMail.setSubject(Const.SEND_MAIL_SUBJECT.CLIENT_REGISTER);

            String otp = DataUtils.generateTempPwd(6);
            LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(EXPIRY_MINUTES);
            DataOtp dataOtp = new DataOtp(otp,expiryTime);
            otpStore.put(sdi.getEmail(), dataOtp);
            Map<String, Object> props = new HashMap<>();
            props.put("name", sdi.getFirstName());
            props.put("email", sdi.getEmail());
            props.put("otp",otp);
            dataMail.setProps(props);

            mailService.sendHtmlMail(dataMail, Const.TEMPLATE_FILE_NAME.CLIENT_REGISTER);
            return true;
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        return false;
    }


    public String layOtp(String email) {
        DataOtp otpEntry = otpStore.get(email);
        if (otpEntry != null && LocalDateTime.now().isBefore(otpEntry.getExpiryTime())) {
            otpStore.remove(email);
            return otpEntry.getOtp();
        }
        return null;
    }

    public boolean verifyOtp(String email, String inputOtp) {
        DataOtp otpEntry = otpStore.get(email);
        if (otpEntry != null && otpEntry.getOtp().equals(inputOtp) && LocalDateTime.now().isBefore(otpEntry.getExpiryTime())) {
            otpStore.remove(email); // Xóa OTP sau khi xác thực thành công
            return true;
        }
        return false;
    }



    public boolean isValidEmail(String email) {
        // Biểu thức chính quy cho định dạng email
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";

        // Tạo đối tượng Pattern
        Pattern pattern = Pattern.compile(emailRegex);

        // Tạo đối tượng Matcher
        Matcher matcher = pattern.matcher(email);

        // Kiểm tra chuỗi với biểu thức chính quy
        return matcher.matches();
    }
}
