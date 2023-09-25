package com.be.wildanimaldetection.firestore;

import static com.be.wildanimaldetection.Activities.HomeActivity.CHANNEL_1_ID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.be.wildanimaldetection.Activities.HomeActivity;
import com.be.wildanimaldetection.Activities.ui.notify.ButtonReceiver;
import com.be.wildanimaldetection.Activities.ui.notify.CallAlert;
import com.be.wildanimaldetection.R;
import com.be.wildanimaldetection.detection.DetectImage;
import com.be.wildanimaldetection.detection.DetectionActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ImageFromDBService extends Service {

    private static final String NOTIF_CHANNEL_ID = "Channel_Id";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference("esp32-cam");
        scoresRef.orderByValue().limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChild) {


                try {
                   // System.out.println(snapshot.getValue().toString());
                    String imgdata = snapshot.getValue().toString();

                    //String imgdata="{photo:data:image/jpeg;base64,%2F9j%2F4AAQSkZJRgABAQEAAAAAAAD%2F2wBDAAwICQsJCAwLCgsODQwOEh4UEhEREiUaHBYeLCYuLSsmKikwNkU7MDNBNCkqPFI9QUdKTU5NLzpVW1RLWkVMTUr%2F2wBDAQ0ODhIQEiMUFCNKMioySkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkr%2FxAAfAAABBQEBAQEBAQAAAAAAAAAAAQIDBAUGBwgJCgv%2FxAC1EAACAQMDAgQDBQUEBAAAAX0BAgMABBEFEiExQQYTUWEHInEUMoGRoQgjQrHBFVLR8CQzYnKCCQoWFxgZGiUmJygpKjQ1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h5eoOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4eLj5OXm5%2Bjp6vHy8%2FT19vf4%2Bfr%2FxAAfAQADAQEBAQEBAQEBAAAAAAAAAQIDBAUGBwgJCgv%2FxAC1EQACAQIEBAMEBwUEBAABAncAAQIDEQQFITEGEkFRB2FxEyIygQgUQpGhscEJIzNS8BVictEKFiQ04SXxFxgZGiYnKCkqNTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqCg4SFhoeIiYqSk5SVlpeYmZqio6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2dri4%2BTl5ufo6ery8%2FT19vf4%2Bfr%2FwAARCAEoAZADASEAAhEBAxEB%2F9oADAMBAAIRAxEAPwCz5aZ%2B6KPLT%2B6KLk8iHiOP%2B7ThEn90UXHyIPLj%2FuCmmNP7gouHKAjj%2FuCpFjTH3BQLlGvGnpWfchfSiIuRGHcop47VS8tP7orVytsDihvlgdB%2BtN21Ny0LspuwUhOFw8setNK0t2LlsLt6UeWKTkPkAJ60bBTXmFkBQUbRSDlDYKBGtPmDkF2rS7BwaExJB5a5pdg7CquLk1EIGOw9Kbs7%2FwBKHMfKh20elHlj%2FIp3sS4i7B9aXYMVnfUFEb5Yo2irBINq0bRU8xXKhNq0mwVXMTyBsFJtFFx8onlj0o2D0pcxPKHlr2FOCDHSncuw7C546U5Yg9SLlO4opNFjqfQAlJQACnigCKRqzbl8GhCZizHt71X61oIaaYOnFFohsFHagltiU009C0FIMEcUtCbsd%2Bf40UXKuJR3osgF%2FlRwBxTsJsSlzSAUdqX86AGP2ob5l470tBDqWpGFGaLAxueeaKoQdqTrSsLUKSnoULRQIOaMUaDCkNFhCrUu7bQrCO4xSYqGaBSg0rALSUgFp1MCCY8VlXJx%2BdUIyCTn%2B6fWovrRYkbTaEVqFJT0ASj0oEgNFIYUVV0OwlFSIWkoAKKokKXNKwxhzn2oPHNHoC0H80mc1OzGFLVb6k31EopDuJS00guFJihWAWigNQzSUaANJpKBCZprE0rjR6KabUsYyloDUM%2B9JmkOw8U40wK854rGvOjc8VSsTZmU%2Babg%2BoyO2attD5Rueabms3YWoppKFYBKCfeq0FqFJj3o0HYMf7WaBU3Q9Q%2FED6mjPuDV3iHKxKOafuisL%2BVFSAlIpzTuTrcD9ab35kU%2B1TdD1JKKr3Q1D8aSleIWFoxR7oBil%2FGjmEBoNPQFcYetH40nZDsxM8UhNLQVho%2BtNdj2NWrDswHHemk1F02XE9KIppFSwGEUw0ANpaYXJEp9SBWnrGvvuYHrTsFzLHFJ3p8ortCbec03aPSm0g1sLgUgFKwCYFIwFOyC4elLiiw7hgY5FJtHvSsINvFG3inZNC5gxRSaKuJtoxQhXFI%2BWo4%2FmbIqrJjbHkD0H5Um1c9KnlFcfijaKfKK4uKAKTQriUY9KOUVxKTHrRYaCm0JDuFHanYVxuKa1OxQlNxU2EIaQ0WNLnppFIRQSRkVGRSAYaKaAkWn0gK01Y1%2F93iqRLMw0vHeqbQwHzLxjHbFNp6CVxPwpKkqwUVSskLQT8KMYouhsWkqbk2ClpBbQT8KKq4AKXFGgCN06UiqetIkT%2BLBo61Q7D6KAQtJUiDFJQ3oNCd6KYCUmKoaA0lQK2ohqLvRcBGpFpcxQGkoKPT8U2kSMYVGRQFyNhTaLDHrT6QXK09Y970NVEDKOaXpT6mcmGf9oUlHLc0TCjtmlaxLbCkxVokTkdKMccms2i9habmr5SQo6U%2BVBzdgpoz6miwrkgp1SPUuRIoQZ60k8XBI9DQmNmc3TrSoG70%2BULkmKSluK4Y57fhQaQBSVVhXG%2FSlPtQlcBlLS5SuYSinYVxDURqbAMahO9Pl0KD60lRYq6PT6Q0CEqNxSAjNMNAAKdTArTVkah0agGZOfmO6lDVTQgpw60xBRn1pNBdDTQKSGhM%2B%2BKSmJCZ5pDQgFpaNgClp3DqLTulICx9rRB90%2Flmh7rzE6%2B3BoSsJ6lIrTkz3cH8Ku%2BgC5ozWdhhmkNFhXEzSVVyhtFMi4h5paQwpDQTca1R0rjI2pyn5aGy7iUlQWeo0lMgSmmgCM1GRTGJSmlYCtN0rHv8Ajt0J4NNaEMyS3PFGePu1Qai%2FhTh%2Fu4%2FGq0Adkgcp%2BtN69qlIENJxzig7vSq5UmTzsac%2BlAz6UNIq409elKMkZA4qrIdxw4FHPWpdhBz%2FAAilFDjoK46kz7ZqOUVxkhpU%2B6K0UUAm4novFLkmlZFXFOe9A57UhXF59KQ59KiwrjTTefSrsO4c46UlBImadSsO4fhQT7UARn6VGetKxQ00A8EYqmhK4n86b%2BFQ0bXPVsUmKkkMUwikAwrTStADNvNBWgCrMtZN%2BCytyfWqSEzJemf560%2BUSFwaUKT%2FABfrUjHgHFJiqQC4puKbBDcGjbSEJtopj3FxSUrALSiiwDucdaTkgD0oIZHIDxT4x8o5FG5QRphduQQPShVI60CHYoAo2KDFJtoEJimlaESJtP4UhFAhu2m9KCh3alFCTGMYVHinYBhFJS6gKRTSKho1R6tijFWZMMUmKRQwim4pANxSEUAVLjpWRefcNUIxjSVpawhO9OBo5SbDu9LSJFo70ihvtTcVemwwoPSoJCimF9QpaBijpmlxR1FzEUuB2pY%2FuDinFARyY3VLHnvQMcaSgoUUZqSRKTiqJAjim1JQ2mkVQDaBRsMUkUx6kCOm0wYtN%2FCoLR6xQBUgLTKQDaYaEAmKawpgVLise9A8uT6VUQ0Mc49OaZVO4goxUu40GafnnHtQToOooGwwKa3B6Gn7xAuKQCjVCENJSKAYpaEMd2o9KeoiOYZxTk%2B7ij3gG8qCViGO%2BXpyfMOmKNUIXFFLUYUlABxSUak6BSZp6jDC%2FwCTTaNQuMYUn4UguJnFNbmn5lDab0pdQCm1DRcWetYoqiRDTTQAymmpGJTWpgU7joax7z7pqkwZkNwfamdaZmMzSZprQaFp2eKVwYoNOqhC55o4%2FD61LuPQDRVBYKbSEIaBRfQGOooAY6F%2FT8acoxSbGQ8nk8496kV89sU0xDqBTbASilcBDSUibCUlO4xKKACkpFDWphoENptNsYnSkNTuaQR67ikxU2I3EIphpFDKZQAU1qYFO471jXn3TVE3MhyM4P8AKoj3oJsNHFGapxKugzS5qVFk3TFDLxz%2BlOGPXn6U9R3HZX1pMjtSjcm4uRS8U2hoTIo%2BX1os0HMJRRYNBcik4zTaYXQtGaSQlJEUWzYBnpSIVJO00%2BUdyTtzSmlqK43txRTsAcUHmpswuhKQ0%2BUeg04pOKdriG0ZFLlGJSHFFmIQ0zilZlXGGkzTs2O57FtpNtQA0ioyKChhplACYprCgCrNwaw7xfkPtTQrGUw5pmKexAm0Gm7D6U5SLENJikmSxcUCnLUEh1OxnimmLlDv70YzRcBccUg%2BlNE2FpadymhdtGKi5FhrD9KMe1UnYtIhfcOsRp0QyM5yKLgSkU0jFRuITFB%2BlV5CDFIalhyhikIqrlIbimlaVwAr7U3FK4DTSH6Ux2EprDmi4DSKTFPnA9kpKyGNIqIimAw0w0FDaRqBlO4rIu%2BhpohmM%2FD49KY1XYOYMU2lyg2JQ2TTsSJzS9uKQ2FKKrlsHMLS5pAJS0rCFzTs0wuOzxSVPKQNNBOKHAq4hpQKOUExvenHNOwJjaMn2q7BcTNJ3zUWBCGkNKw0wpKOUdxppKLAN70hptCG9qTNFh3Epp60uUZ7HRUMBpqNhQBE1R0ygprUgKU9Y15xGx6cVQGS23f8pph%2BtUJoCKbgY5paiG%2FKRw1HYUXew2h3T3pKaMxMUvFDKFpKkYpFLinzCClx6mnewgHBp1HULCY96KG9QVheCKb%2BdNCCgUPQEBpvSp1DQMU2ncSEpCaRVhPxpeBQ%2BwhlJTWhdhppvbrSbFYSkp3EhueetIaQ7HslJUANNMNMZG1RmkUNpr0wKNz0NY16eDj0qkSzJJ%2BamE5qyeggz3pOtPmEGaCTis7a3HcG7YpOlU9BaBRRcq2gZpanqIM0vSmwFpaVxBTqpskbnFLml1ASim2AHrRUjEpKdwG0hoT1EFJRsWJTaaGNNFAhKaaTAbSUIYlJSA9joqBiUw0DImphoGJUb0wKFz0NYl99xqe5DMclvWl5xVSVhaBzjqv9abUgGM9KX8qeoOwUHNGoaCc5xQeKnUYfTFIaqNxBTutFmAcCnCp1DQXp1pBVtEh1opa3sPYO1JQ7kiYpaeowpMUtQQ3p0opgxDSHrS1K0EpDRqA2ip1GJz7U1qoNBp6UlIY2ko1Foey0YpAJimEUrjIyKjIoGJio3FMZQuaw79cqaaIZkFcU3aPTFa8xLQ4UYqL3FyhsGDj0pBVJhyi0BPSjYpCbBRtoUgExzS7e9K5DQbaNtJyAMU7FABgdqKu4WCiouJBikxSuyheKbVpgGKTFFxBim4oTBRDFAFIYhFNwB0quYVhNtNqblIQ0mBRzBYbikqWMbSUwse0YoxWYxCKYaAInplBQVFJSGZ11WFfYwc1aJZlMRmkpsQClqbDE7UVSELgUCjUpWA0cUE6BxSUDFooExOM0oxmqJHUEe1K4DOMdKdS5QEo7UIYmKSmLoFFIQlJQtRicUUAJSUikJTaaDQQ02gBtJQMbSU1qB7VijFZMkQimEVQyFhTKBoMVDLSKMy66GsO9q0ZsyBgdgeejDNKabGJS8%2BlCfcXL1Eop3EgoqrjsO%2BtHfn9KjmJsJQOBQ5FARRir50SKKF60EjhmkpBYSihstDaKbZIGkqVIAzSUXHYQUUXJEooCwlNpFJDc0UyrCGm0XEIabU3GJTe9Fwse3UVLAaRUZqREZplMtCVXmoGZV3WFf%2B3WriQzL9qj%2FOncYoPNPGKNBDQeKXPFCVwCgdaYBmlBOKTQMKXNAgoo3AQ%2FTIpaYDqWmSJRSENPWkoEBptBVhCMUlBQmabSAdnikzSEJmkp2AbRTKuITTCaQgPSm9KQ7CZptAHuWKMVFxDSKjYUhkLUymUBqvLRcDHvK57UTzVxZLM09KQmqkFhB70fg1K6Cw7oPWjt3pc4couaT8%2FwFPnQWFFHHvSuFhetJ%2BdPmCwv50fnSuFg4p1HORbUWk6VVxhzRUtkjc9hRVXHYM%2FWm5pKYWCkp30ATrTam4WEpM0XKG0ZobCwlJmq5hhTM1NxCU3pT5gQlJmi5XLqe7UVBA01GaQyFhTcUyhCKrT9DSGY970Nc7qPLEe1XEhmfimVT0Juw2806kinIO1Jiiw2wFHPamwHdqTkURBi4paSBB2oFMli0YpisOHNIRQIKTDUFWE70fWpJExSbaoBMUnNIYlJS2EJim07DExSUxiUlKwaiUlJDG02mAlJinsUe80lYEDWFRNTGRmm4oKA9KqXFAGNe965%2B%2B5erQGcRyaSjqSIVzjn8qTtTQWF60lJjYU7nPFAC0UkwEoNNMBaKAsJTqfMDFzQKpMmwtJntUiGGlzxTAbmjNFwSEJpu6kOwlNzSuAUlVcaQ3NFSMSkpjG0UrisJTcURKExSYouSe8UlSSNNRNQBHSUihGqlc00Mxbw1gXPLe9UhlJxg1FzmqsiGmKBikNKwWaD8D%2BNJSASg80DF6YoLU42YBR9aEOwZozmggM0UBqLS5qtBBmm5o0YxM0lUFhM0m6p0ASkzQAmaSpEJSGgpCUZp2ASkpJCDNJTKQlGakBKTNGgI98IplSSRtURpARmlFJFDZOlZ9yeDVAYd4eDWJMfmNOwis%2FNMqmWgpKS0LuH5UhAoTDQTZSFaS3FYbtP50hSmKw3GaDmncBKSkiGL%2BNANMGOzSbqqxItFSFxtBqgYlIKQDTS0gG0ZHrTAbuFJuFSFxu4UbhVDuGaM1LAKQ0FDaSgQmaSkkM%2BgitRkVLMyJ6hagYylpDI5KzbvvTGYN9yGrHf71XcCBqhPFJDG%2BZzjuKPMHrQ7IoN69mB%2FGl3f7aj6mlcdhc4780D9KoQZp2cCluWhvfmk4qW9R2DA9BTSop82grCbBSbV70XFykbuq8Dk%2BlQ7n9a0XmZsPMk%2FvfoKXe9V7pNhC7mk8yX1%2FSpuhWDe%2FY%2FpTcv%2Fez%2BlLQY3LH%2BKl59aegg3Ypc5qboAowKq4CbaTbS5hibaQjFAWEOaMtQAZpN1A9Rw96OKQj6EYVGRUkkL1A1IBmKXFBRFL0rKu%2B9NDMC%2FwDu1lPVDuQkVGRilYERtj0qNlqbDIiaBzWnsybjl5PFOYsKXKjS4KxPSl8xu4pcpQed6igTp7%2FlQ4juLvSnbx61FhXE9TUDSbuKvluS5Ee2nba02M27jgtJjFKwJ2F20uKCeYbto%2FCluFxu0UhX2qrDTuJ5YpuypsFwK0m2nYLiYNJ83rU8qATmjd7U7ALupMg9qVig4pClBQuyl20rEn0IaiagxIXqE0FCAUuKQyCbpWPeHrTA5%2FUDWY1O5drkTHimmnsMjJ9qZjHofpRcViNgOn9KaFGaFIrlJQoFIy5%2Fi%2FSlzDSHKm2m45OcZPSjmNBnlvsPHf1oWH25p8yFa4vlr6Ux9i9uaVyGQ981IuadzNjtvFKK05jNi0mKi5SQneiquhCGkqbgJSVfMIKDU3HYSkpXCwlJxTuOwYpNlK4CbaNgpXDUXaKKlSLCjOKdwSPoE1G1JoyIHqKkMBTiKRRUuOlYt6etVYRz9%2B3NZrGnYpMjNNosWhhppo5RXGmozSHe45W5p4NOw%2BYXNGeOtTy9S7iHNMMir%2FEM07CvYhkmPbioaqxFxRmpEY4xS5SZMfSirJFopWI5tRtFUXcSkqLC2G0tOwDaKTQriUmfWgq4maKLCEFFDKuLSUhi0YosFxKYaRaPoU1E1BzkLVGaQxQKc3SkMoXfQ1hXp600I52%2BPJqgTVGo002puAw5zn1pM0aDGHmmmqH0Gn2oV%2BfagCTeD0xQzhepFTy3BleS4444981Hnd1q9kG4hopbALRnBoFYnFFUZi9aaaBCZpaQwptFxMSimNCUlJsEJR0pAJRQAUUkwFpRQUhCwWoFn3t7UFWH00moGfQ5qF6s5iE02kUOFD9KAM28Nc%2Fet1pgc3dt%2B8NVufTpVWRfMR8%2BlIW9qViuYad3939abS5R3GEHsKaSaLE8w2mk1ewxu8joKYWNSFxPwpPwoAOaWi3YdwpaLAySNqfzTRm7hR%2FDQSNz7UZp8pVxDmlxSDmG0UALSU0JMKTFTIYmKXFAgoqGUFQTXO37vJq7XHcp72eQNT4z81Fi7lik70rDufRJqB6GcpEaSkUPFMl6UDMq9PBrnb1utVYRz1wcvnNVjz1zRbU0Qn40lJxGNI%2BtMxU2HsIabilqNajcU3FPUBtMYVQDaKbQri0YqRsTvUnNADRwanqgYYooMxpHvS4oYxDRRYQmKSiwXEopCDbRg0xi4pcUrDCmOwWlYCpNKzd%2BKr07FAlOFKw%2BhZHIpMVFhn0QahetTlIqBUjH1DMaBmNfP1rnL9%2FleqEYMh%2Bao6dixtJmlYrW4VG1Ciihvemmiw7jaKdhDTTKSGhhpBT2AdxRSEFKKYxacjcYoJZJSHiqMwPSk61IxKWqASjjtSATFGKEhDqNtSAcUlBRDJJioGbdRoaET1EaBCA06iwXLlphkIPUGpjGKke57%2BahemcxFS0hi1WuOlA7mHfE4rmtTbgj1qrAYzHqKYaC0xM0bqGh8w3v9Kb1pLUu4lMoWoridabRcY2inYojfrRQ0SwpaXKO4UU7DDrSZxR0EywDkUhoXYyYUU2FwoqQEpab0AXFAo30AXoKYzUkNITNQs%2BelMuxFUeagBrCoapANp1GwkOVyjAgnitRW3AUh3P%2F2QAA}";
                    byte[] code=decodeBase64(imgdata);
                    //System.out.println("code: "+code.toString());
                    Bitmap bitmap = BitmapFactory.decodeByteArray(code,0,code.length);
                    DetectImage detectImage = new DetectImage();
                    String resultDetection = detectImage.detectImage(bitmap);

                    if(resultDetection.equals("tiger")||resultDetection.equals("lion")||resultDetection.equals("leopard"))
                    {
                        createCallNotify(resultDetection,code);
                    }
                    else
                    {
                        NotificationManager mNotificationManager;
                        NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(ImageFromDBService.this, HomeActivity.CHANNEL_1_ID);

                        NotificationCompat.BigPictureStyle bigText = new NotificationCompat.BigPictureStyle();
                        bigText.setBigContentTitle("Animal Detection");
                        bigText.setSummaryText("Image has captured");
                        bigText.bigPicture(bitmap);
                        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
                        mBuilder.setContentTitle("Animal Detection");
                        mBuilder.setContentText("Image has captured");
                        mBuilder.setPriority(Notification.PRIORITY_MAX);
                        mBuilder.setStyle(bigText);
                        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
                        mNotificationManager =
                                (NotificationManager) ImageFromDBService.this.getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.notify(0, mBuilder.build());
                    }




                  //  notification(bitmap);
//                    NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                        NotificationChannel notificationChannel = new NotificationChannel("3","Simple_Notification", NotificationManager.IMPORTANCE_HIGH);
//                        notificationChannel.setDescription("Alert from wild animal detect");
//
//                        manager.createNotificationChannel(notificationChannel);
//                    }

//                    String chh ="fcmchannel";
//                    NotificationCompat.Builder builder=new NotificationCompat.Builder(ImageFromDBService.this, chh);
//                    builder.setAutoCancel(true)
//                            .setContentTitle("Alert from wild animal detect")
//                            .setStyle(new NotificationCompat.BigPictureStyle()
//                                    .bigPicture(Bitmap.createScaledBitmap(bitmap,100,100,true))
//
//                                    )
//                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                            .setDefaults(Notification.DEFAULT_SOUND)
//                            .setSmallIcon(R.mipmap.ic_launcher_round)
//                            .setLargeIcon(bitmap);
//
////                    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
////                    builder.setSound(alarmSound);
//
//                    manager.notify(3,builder.build());


// === Removed some obsoletes
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//                    {
//                        String channelId = "Your_channel_id";
//                        NotificationChannel channel = new NotificationChannel(
//                                channelId,
//                                "Channel human readable title",
//                                NotificationManager.IMPORTANCE_HIGH);
//                        mNotificationManager.createNotificationChannel(channel);
//                        mBuilder.setChannelId(channelId);
//                    }



                    //openAlertActivity();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                // Log.d(TAG, "The " + snapshot.getKey() + " dinosaur's score is " + snapshot.getValue());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            // ...
        });


        return super.onStartCommand(intent, flags, startId);
    }
    private void startMyOwnForeground(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelName = "Image Service";
            NotificationChannel chan = new NotificationChannel(NOTIF_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIF_CHANNEL_ID);
            Notification notification = notificationBuilder.setOngoing(true)
                    .setSmallIcon(R.drawable.tiger2)
                    .setContentTitle("App is running in background for Images")
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build();
            startForeground(3, notification);
        }
    }

    private void startForeground() {
        Intent notificationIntent = new Intent(this, HomeActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        startForeground(4, new NotificationCompat.Builder(this,
                NOTIF_CHANNEL_ID) // don't forget create a notification channel first
                .setOngoing(true)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Service is running background")
                .setContentIntent(pendingIntent)
                .build());
    }
    private byte[] decodeBase64(String coded){
        byte[] valueDecoded= new byte[0];
        try {
            // coded=coded.replaceAll("%2"+".+?","/");
            coded=coded.replace("{photo=data:image/jpeg;base64,","");
            coded=coded.replace("}","");
            coded=coded.replaceAll("%2F","/");
            coded=coded.replaceAll("%2B","+");
            valueDecoded = Base64.decode(coded, Base64.DEFAULT);
        } catch (Exception e) {
        }
        return valueDecoded;
    }



    public void createCallNotify(String detection,byte[] code)
    {

        Intent alertSound = new Intent(this, CallAlert.class);      //alert sound ;
        startService(alertSound);
        Intent notificationIntent2 = new Intent("android.intent.action.MAIN");  // alert window code;
        notificationIntent2.setClass(this, DetectionActivity.class);
        notificationIntent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notificationIntent2.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        notificationIntent2.putExtra("detection",detection);
        notificationIntent2.putExtra(Intent.EXTRA_INTENT, alertSound);
        notificationIntent2.putExtra("image",code);
        notificationIntent2.putExtra("notificationId",7);

        Intent buttonIntent = new Intent(this, ButtonReceiver.class);   // close the notification;
        buttonIntent.putExtra("notificationId",7);                         // send notificaton id ti the intent to close;
        buttonIntent.putExtra(Intent.EXTRA_INTENT, alertSound);

        PendingIntent pendingIntentClose =PendingIntent.getBroadcast(
                this,
                7,
                buttonIntent,
                PendingIntent.FLAG_ONE_SHOT
        );
      //  PendingIntent pendingIntentClose = PendingIntent.getActivity(this, 0, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent2, PendingIntent.FLAG_UPDATE_CURRENT);

        // RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.custom_notification);
        Uri alarmSound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alert1);
        NotificationCompat.Builder customNotification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                .setSmallIcon(R.drawable.tiger2)
                .setTicker("Call_STATUS")
                .setSound(alarmSound)
                .setContentTitle("Emergency Alert")
                .setColor(Color.RED)
                .setVibrate(null)
                .setOngoing(true)
                .setAutoCancel(true)
                .addAction(R.drawable.bell,"Open",pendingIntent)
                .addAction(R.drawable.close_icon,"Close",pendingIntentClose)
                .setContentText("Wild Animal Detected")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setCategory(Notification.CATEGORY_CALL)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(Notification.PRIORITY_MAX)
                .setFullScreenIntent(pendingIntent,true)

                ;



        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ImageFromDBService.this);

        notificationManager.notify(7,customNotification.build());
//        sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));


        startActivity(notificationIntent2);
    }
}
