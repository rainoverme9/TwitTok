package com.example.twittwok;

import static com.example.twittwok.utils.ReuqestUtils.CommunicationController.GetApiInterface;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.Sid;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void registerCorrectRequest() {
        Call<Sid> registerCall = GetApiInterface().register();
        try {
            Response<Sid> response = registerCall.execute();
            assertTrue("Registrazione corretta", response.body().getSid().length()>5);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }
    /*@Test
    public void getProfileCorrectRequest() {
        SendSid sid = new SendSid();
        sid.setSid("wVOGrbZIqM1gRbOyz0q9");
        Call<GetProfile> profileCall = SendRequest().getProfile(sid);
        try {
            Response<GetProfile> response = profileCall.execute();
            assertTrue("Registrazione corretta", sid.getSid().length()>5);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }*/
}