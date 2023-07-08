package com.idea.MyBook.Service;

import com.idea.MyBook.Repository.SourceLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class SourceLinkService {
    @Autowired
    SourceLinkRepository sourceLinkRepository;
    String getLocalLink(String unurl){
        String ipv4;
        try {
            ipv4 = getIPv4();
            return ipv4+unurl;
        }
        catch (UnknownHostException e){
            return "404";
        }
    }
    String getIPv4() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
