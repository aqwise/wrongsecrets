package org.owasp.wrongsecrets.challenges.docker.binaryexecution;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
public class MuslDetectorImpl implements MuslDetector {


    @Override
    public boolean isMusl() {
        ProcessBuilder ps = new ProcessBuilder("ldd", "/bin/ls");
        ps.redirectErrorStream(true);
        try {
            Process pr = ps.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream(), StandardCharsets.UTF_8));
            String result = in.readLine();
            return !Strings.isNullOrEmpty(result) && result.contains("musl");
        } catch (IOException e) {
            log.error("Could not detect musl due to: ", e);
            return false;
        }

    }
}
