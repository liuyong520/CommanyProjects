package com.nnk.upstream.util;

import com.nnk.utils.http.utils.StringUtil;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class YhbfShell {

    private static final Logger log = Logger.getLogger(YhbfShell.class);

    private static final String commandPath = "/usr/local/007ka/bin/yhbf";

    public static String decoder(String encode) {
        if (StringUtil.isEmpty(encode)) {
            return encode;
        }
        String command = commandPath + "  D " + encode;
        Process p = null;
        InputStreamReader in = null;
        BufferedReader bf = null;
        try {
            p = Runtime.getRuntime().exec(command);
            in = new InputStreamReader(p.getInputStream());
            bf = new BufferedReader(in);
            String ciphertext = "";
            String line = null;
            while ((line = bf.readLine()) != null)
                ciphertext = ciphertext + line;
            encode = ciphertext;
        } catch (Exception e) {
            log.error(e);
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            if (p != null) {
                p.destroy();
            }

        }
        return encode;
    }

    public static String encode(String decode) {
        String command = commandPath + " E " + decode;
        Process p = null;
        InputStreamReader in = null;
        BufferedReader bf = null;
        try {
            p = Runtime.getRuntime().exec(command);
            in = new InputStreamReader(p.getInputStream());
            bf = new BufferedReader(in);
            String ciphertext = "";
            String line = null;
            while ((line = bf.readLine()) != null)
                ciphertext = ciphertext + line;
            decode = ciphertext;
        } catch (Exception e) {
            log.error(e);
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            if (p != null) {
                p.destroy();
            }

        }
        return decode;
    }
}