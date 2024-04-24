package com.example.demo.Services;
import com.jcraft.jsch.*;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@ConfigurationProperties(prefix = "sftp")
public class SftpService {
    private String username;
    private String password;
    private String host;
    private int port;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }
    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }

    public void uploadFileToSftp(MultipartFile file, String remoteDir, String newFileName) throws Exception {
        JSch jsch = new JSch();
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;

        try {
            session = jsch.getSession(this.username, this.host, this.port);
            session.setPassword(this.password);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(remoteDir);

            try (InputStream inputStream = file.getInputStream()) {
                channelSftp.put(inputStream, newFileName);
            }
        } finally {
            if (channelSftp != null) {
                channelSftp.exit();
            }
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }
}
