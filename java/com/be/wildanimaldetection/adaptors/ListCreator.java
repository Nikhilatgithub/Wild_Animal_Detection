package com.be.wildanimaldetection.adaptors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.util.ArrayList;

public class ListCreator {
    ArrayList<ReclycleGetSet> arrayList=new ArrayList<>();
    public ArrayList<ReclycleGetSet> getArrayList() {
        return arrayList;
    }
    public ListCreator() {
        try {
  String data1="data:image/jpeg;base64,%2F9j%2F4AAQSkZJRgABAQEAAAAAAAD%2F2wBDAAwICQsJCAwLCgsODQwOEh4UEhEREiUaHBYeLCYuLSsmKikwNkU7MDNBNCkqPFI9QUdKTU5NLzpVW1RLWkVMTUr%2F2wBDAQ0ODhIQEiMUFCNKMioySkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkr%2FxAAfAAABBQEBAQEBAQAAAAAAAAAAAQIDBAUGBwgJCgv%2FxAC1EAACAQMDAgQDBQUEBAAAAX0BAgMABBEFEiExQQYTUWEHInEUMoGRoQgjQrHBFVLR8CQzYnKCCQoWFxgZGiUmJygpKjQ1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h5eoOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4eLj5OXm5%2Bjp6vHy8%2FT19vf4%2Bfr%2FxAAfAQADAQEBAQEBAQEBAAAAAAAAAQIDBAUGBwgJCgv%2FxAC1EQACAQIEBAMEBwUEBAABAncAAQIDEQQFITEGEkFRB2FxEyIygQgUQpGhscEJIzNS8BVictEKFiQ04SXxFxgZGiYnKCkqNTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqCg4SFhoeIiYqSk5SVlpeYmZqio6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2dri4%2BTl5ufo6ery8%2FT19vf4%2Bfr%2FwAARCAEoAZADASEAAhEBAxEB%2F9oADAMBAAIRAxEAPwCfZH%2FcFJtj%2FwCeYrNk8obI%2FwC4KXZH%2FcFA1EAsf9wUu2P%2FAJ5ikPlDbH%2FcWl2R%2FwBwUByhsi%2F55rS7I%2F7goCwbI%2F7gpuyP%2B4KA5RCiH%2BAVWuysURO0U0FjmpI8E55qMRr%2FAHRW9wsizDAvdRVryosf6tazbKURpij%2FAOea0bE%2FuCkVYbsXuopPKT%2FnmKWwcoeUn90UmyP%2B4Kq4WG7Y%2FwC6KMRf3FoQrIcscf8AcBpTEn9wUwsivJEn92ovLTP3RV3IcRDEn90U3y1%2Fu07gHlp%2FdqxZopu4QUBy1JvQXKdb5MWT%2B7Wjy4%2F%2Bea1ylWG%2BVH%2FcFHlx%2FwDPMUByjfLj%2FuCk8uP%2B4KYconlR%2FwBwU0xx%2FwDPNaYuUPLj%2FwCea03y4%2F8AnmKQco3y4%2F8AnmtJ5cX%2FADyWgdhnlx%2F88xTDHH%2FzzWkKwhji%2FwCea0wxxf3BTQWNY0lUAUUAFLQAtFK4wpaGAUuKLjFAqnqMXmeUOihsmiO4mc%2FcAMx4%2B7TYIt1b30EXVSnkVm2aIZtoCZoLF2D0pjkLRcRA0oqB5s1SJGeYTRkmmQOHWrKP60hoWSPK1VK807iYmKNgrS4hMVNCP38IXrvFK4W1OvxyaMVylDcUmKBiEUlAhuKTFMBpptAhKbSuMaajNICN3VaqPdjoEP1poR0FJVMQlFABS0ALSVIxaWkMcBThSGLUN7HutZf93jFNAc9eReW8n4VLbxYiU461v0ES9KTIqCrjNwpc0DGM9VZmzVIVyuRTdtaEEiQbu1WY4VHaobAl8sUGFaSKHhMdKqzx7TnFOO4MhxS4rQgNtWtKi8zUrYej5qWM6ijFc4xMUmKAExTSKAEpmKYDaaaAGmo2YCkBXkukX%2Faqm9y7dPlHtTsIrt1ptWM6k0VLZIUlIdgop3AKdikOw4CnVIC06gY6hhkYoGYevQbBGw%2F5afL%2BVI37qFc1pe5Jny3PpUXnse9WkIejGpd1LqVYYST1ppFO4yM0KnNNMmxOvFSA1Ix604UxkgFMeLdRfUkpyRbD0pu2tCbBtrS0BP8AT2P91OKJP3QRv4oxXKUJikxTATFJigBmKaRQBGaiklVepoEVZLk%2Fw8VUd2b7xzTAhNMpgMpDTKOpNJUkC02gYUtSMcBTqQDqWgY4U6kA6nYpDMvxLHmxt2%2FuzfzFYVxJuI%2FSuiBJD5bH1o8qnckUDHSpkoZdx3lmmlKAIyKaXxVWAPMxQs3NKxDZN5tSI%2BaLFXLEdT4qRkU8QZelUsVpEhi7a09BT%2FSpPZKU9gRt4oxWBQmKTFADcUlMCNzjrVWS5A%2B7zQIqyys3eq5qwGMaiNIBhqM0DG4pdlIR1JptTcQlFK4wxTqQxadUjHClpiHCnigocKdQBU1ld2lXH%2ByN361kfYyIw7DtWsdiSGRcVVkGKvmHYiqSDqKQJF3FMZakqxAy%2B1VjEx5IrRSJsDxHFIsZquYhonWImrUcWBUtjROq1MtSUDLkVRddrmriQxNtaOi%2F8f3%2FAACiQkbmKMVgWJikxTAbiobjKxkigDNkyetRNWhJEaYaQ7DDTMUriG7KTbSuVYTbSYqbgdKabigQmKKQxcU6kMUCnUhCinUDHCnAUwHgU%2FFSMZcw%2BdazRf30xUg01bnw7bzR%2Ff8AK5%2Bo61rAhnKTnGeaz3f0rVIbGHdV62TODSYIsmkqTQYVppioQhrRUqx1QtSZEqYJSEP2U8CgQuKqTx%2FMauJLGBM1ctg0PzA81chI1rGf7RlWxvFWJGSJcu2K57FCSbo2CyRvGWGRvQrmkxQMTFRypuQj1oEZbrULLVoRGRTClAxNlNK0gGbabipAaRTTUjOkNJikITFFIYtLipAWnYqhi06pAcBTxQMeBTwKAHgVb0cCLTbiDBxGzN%2BDc1pT3JnscJdrHNBLKs22USECAjqnY1meWa6NhItRRVoRRbUrK5ohSlMK4qSgxQKYhwFO2U2IcBT6QmOFOFMkftqKZKtEsIoqmEeaYEtri3uoJH%2F1fmDf%2Fu967%2B0060subeBVb%2B%2F1apYGX4ri3JZSgH5JsMRngFTWQFpMEIVphFSMzJY8MagZasQwrTSKQxpWoytIYwimEVIDSKYakZ0dNoZAlFIoWlqQFpwpjHUtAD6cKQyQU8UDHgVFcOIkb%2FaGKa3EzD1SKErGY1G7viszycVvclFi3g%2BbjvVidTDwwxU7llbzKUSA0xD8A1CeDQhkq06gQ%2BlpiHCpRTEPFMkWqRLHRrhanSmSLMmYmx6V3893b26bpZkUfWoa1K6GDrOsQ3lqbeCNmDMDvPHQ5qkooaBMUrTGWpGZ90n7yqjLVCI8U0ikAw1G1IZGajNSAw0w0hnR0lIQlLUgFOoGFOoGOp1ADhTxSGSCpAKQx4FU9V%2B5EPc00DMiXmodlaiJYRjLf3Bk1Uub9bjhvlK9KpIXUpGU1D5rhsjFVYVy3FOT1qTduagdyVakqRiiniqsSPWpVoESgUhXNaIlk0Ufy1MsNMksxxd6cyZOcc0xNjDEauJHxUSRURxSomWsyyjeJ0NUHWmIhIphpDGGmGkMiaojUgRmmGpA6SkoZIlFIoWlpALTqYx9LUjHinigCRalFSMeKo6j95fYU47iMmQc1E1bDGrMYxKOMSLg1jTRkORWkSGIuDRVEski5q5GtSy0T06kAop60ySRRUyimJkqipBHVkMtQxVeits9qsRcismKFsVKmms3bH1p3SETLpCfxuf%2BA0xrIxcduxqObmKIHixVaRKzZZSu0%2Fdn2rMcUgICKjahgRmo2qRkLVEaQyJ3A6mqz3Kjoc0LcR1tJUMQlFIYtKKChacKAHinCkA4U8UhkyipAKQyQCq91B5ob%2B9jinEDClHNV2rYRHiqV1jPC%2FN6047iZWwaf5Wa1IBUxUyuRS3AnWap0OalodyQCnAUwJ0FTBaYmTxrVqOOrRDNG1t8mtm3tAvWr2JLQAUYFLXOaBRQgZTuIqzpkqmJFKZMisiQVJRXaoWoGRNULuB3pCKs04FUpLqmoBcpySsagLE1VgPQqbWDASlpDClqRjqcKBjhTxQA4VIBSKJVqVRSAkAoI%2BYGhbgzF1KDY9ZT9a6CEROwUVTb52zVIbDdTM1SIEp4pgPxU0J5oYFoVIKkCeOrKCmIsxpWhbx5rVEG3ZQBV3GrlRUY4oKKzLCigBkgytZ061ZCKEorHuV2sRUFlJ%2BKrSPTC5UllqjJIapIRVkJNQNT6ARGmbc1FxnoNJWABSUALS0ihaeKQx4pwoGOFSLSAmSphUjHilI4oGVbqLz4s45Fc7OnzmukyM66%2B%2Fiq7GrSC4KjN2qQW57mgoUxKKbspokcENSoKYi0lSrSAsIKsx1RJchFalovNaRIZuIMKKdWEty0FFIoKKACqNwtX0IM6YVj36%2FNUFGXN0qlIKsRVcVXYUwIGjNM8mkUN8kUm0CoY0dtSVkISipGFLSGLTxQA8U6gY8VItIZMtSCpGIbiJCAZBk1ZxQMhV9l75R6TL8n%2B9WTqduEnJA4NdEdjJ7nO3y4lNQ2sJmuFXBrUCxc%2FuZNveq5mpWHcb5lPDUEkq1JTAlSpkoQFlKsxVRJfgFa1iPmFaozZsUVzPc0QUUFBRQAVXuRVokzJxWVqC%2FKPrUjMiaqbrTGQMlQmMUriIiKiYUiiJhTCKQHYUVmAlFIApaBjhThSAeKeKQx2acZUj%2B8wFICF9RA4jTPuaryXcsn3n49BVcoERlwM%2BnNdU3PPqAamRSKl9CXiLr%2FAKyL5kqrcSrcQ7vUVpTM5HO38fzVXi%2Fd%2FMvBrURDcuZDuY5NVGBrSIDkBNTqhoAmWpBSAelTpRYRZjq1FTRJoQVr2H3hWqIZrUVzM0QUUFBRQAVFP0qoksy56zbwZif6UhmLKOaqNTGQuKhapAhaomFAERFRtUDOvpKkQlFIYUtIYop4oGO3Ad6ia7jHT5vpSEQPdO3T5RUOfeqsAZpaYxWBaNgO4rrYjvhib1QVlItEsS5lQeprmbB9yyRtxskYfrWlMzkVdSj71n44rYSITHmmsu2gBg4qZGpiJRinUhjhUymrEWIzVqKqJL0BrYsTyK0RmbFFczNUFFAwooAKZL9ymiWZdxWfKM8UMZhzdTVKSgZE1QtSAiaomqRkLUw1LA6402kISigYUZpARtcKvvUbXLH7vFOwyIsT1NJmmAtFIZIq5px2p97vQBXa58sggqcdga63SJRcaVbyj3X8jUzQRLY4YH0rGgsYz%2Fazp5jXVvOxWNVznPIqqOrCRVuohcRcfpWe1rt9a1IIJE21SkPNMGQ04GrAkVqlV6VhEoNPU0xFmM1bjNMRcgNbFk3NWSbaHKilrCW5aCikUFFABTZPu00SzKuOtZ81JjMa8G2VvzrPkpjImqE0gI2qJqkCI1EaRR1W6lpEhRSGQyOQeKgZietFgG0maYxaUClcY8LT1WkMlUVFfRGWEAdjQIS3sYWGGTJHPNdB4bOdHAC7RHPIuKU2NGnUejNt1%2FVo%2FVIJPzBp0hSGaxphSR7m1jZu8iD%2BdYDlWG5TkGtzMoXZ4rJk600O5GaSqJHg08NTsBKr1OhosBYjNWo2oEXIWrUtpMUyTds5Ny4qxUTKiFFQWFFABTZPu00SzJuDWdKaTGZV%2FwDeB9azJDQMhJqJqAImNRmoGRMaqvcID60lcZ2NFBIUlICJ%2BtRGgYwikpjHKKkAqCh%2BKcKQEgok%2FwBU3rimIl0x0QzecwAZML9a1NCx5FwF5XzAwx9KJAjSqKwG3xNOf%2Be1nHj%2FAICxpRKN2uX8QaPKkkt7Zxgp96SNTyfVq6UYHLSyLKuQQaz5RirArGkqhMUGpBTAkQ1OppAToatRtSGW4mq%2FDJ0piNS0uNprZikEgyKUkJD6KyNAooAKhuG2rTiSzJnbms%2BU0mMzr%2FmLPpWTIaEMrk0xjSAiY1ExqRkT1lnrVREzujlaUNmpAdSVIEcnWo6BjaSgY9afSKHCnVIhwqRRnj1pgRWsLXNtG8T%2FAL3djA4NaHhJv%2BQhER93B%2FWrnsJG9UA%2BXXdPb%2B%2FHLH%2FI0qXxDlsb1FamZ5xrEHl6neQtgSeZu4OeDzWRMOvtWpJUYVHVAOFPBosIetTRmgCylToakotRtVqN6QFuKatC3vCn8VWSaUWpJxv%2FADqf7ZD%2FAHqn2YuYb9tjpPt0feq9mieZgb%2BEdzVC71BH9V%2FWo5LFcxny3duf%2BWw%2F75NVJLmH%2FnqtZ2ZdynPNC8bL5q%2FMKy5BRYOYhMZqN46Vh8xEY%2FeomQ0WHzFaQSLzsOKzH4ahILnfHd7UzB9KgCSipGRv1qOgYlJTAetPqBhS5oGLvA70n2pF75qhMrfaii7IpGC5z8ta%2Fha4DXc8IQgtHnOfSnJaEo6Oq9yP%2BJjo7%2F3blx%2BcTVnF6lm%2FRXSZHA%2BMlKa6WwRuiT8etVZLZb2wjniyD06VtIgyZ7YoSKpsmKVygxSii4rEi1PGKQ7E61KtSMmRqnV6YEyyVMs1USSfafenpeH1qiWS%2Fafemm596CBhuTUEs%2BaQyq8lQNJUFEDPUTPUDImkqJnoERl6ZvoAbvxUTvu%2B9zQM6ukqDQKKkCOSoqBiUlBQ5TTXuVWla4r2K73p%2FhqBrqX%2B%2Ba25EK43z3Pek3E9aLCHrkmtrwuDHrcWejqVqZbDR1lV9Q%2BWO3kyf3d1EeP97FYwVy2dBRXQZHGePEAu7R%2B7oR%2BVUvDcm%2BCe1bHy%2FOvPrVvYSC7tvN%2FhGfpWNdQYakMpMMU2qsK5ItTJQUSg1IDUgPDVIGqkIlD0%2FwAygQhloSXmqIZP5tJ5lBInmVE8tSMgZ6hZ6QEbtUTPSsBETUZakBHmmlqLARF6WgaOtNNrI1CikMjkqFmAoGQvcoveoGvP7oqrMVyBpWbqaZurRCYnWnKpNJhYlS2f6VbjtB3NS5DsWo4VFaGl%2FJqdp%2Fv4%2FSs2UdFVPW%2F%2BQJfEdVi3D8DUw3BnSDkUtdBBy3j1f9DtG%2F6akf8AjprmdFnMOqWw52yPsYA9q2%2ByQb04%2BY1kX8fWsSzFlXmoqq4DxUimgB4NPBoAkDU4NQIcGp2%2BmAhemo%2FNUQT76XfSJGl6jZ6QEbNUTNSGRFqiZqAIyajJoENzULvSGItPzQM640lZs0EpakCGY8VTl%2BYVSBlUwmmmJ%2FStOYQnlP3GKkFt6mlzD5SUW6f3RUyoo6KBUtsZIoqZagZMtTW7bLiFv7rikM6Y8MahvU86xuYv78TD9KkZsWEvn2NvL%2FfjVv0qeugzMHxlbNcaSPLTe6SrgfX5a427Y6ZaTJEV%2B0kfM4%2Fh9qvdEHQS%2FwCpicfxRqf0rLvDkVBZizdagpgJTgaYD808GgB26nbqAHbqduqiWRySUQtTIJw1BakIaWphakBGWqNmpARE0wtTERE1HmhgNdsCoM0DRIOlGagZ2JpKzNRKKQEU3Sq5qkAw0lMBQKdip1AWloKHipRUjJRT920Z9KAOoY80qcsB68VC3GTeGJPM0K1%2F2AY%2F%2B%2BSVrTZgilmOFHJNdJkclrWqSvK585lgH%2BrQcbveuPnk824RP9rmtDJbnR6Rc%2FadKUNy8LNGeegzxVW9GM1mbGNP1quaBiUZphceKXNAC5pwNMTJM0FqoybK0r%2FPViH7tMCbNIWqREZamFqAGFqjJpCIy1NJpgRE03NIZBK%2Fam0DHoaGNSB2ZptQaBRUjIpelQU0AhpKYwFOpXAcKTNFwJAPY04GpGO8wCo572FIHO7cdvQUWuNHXI26KJv70an9KerbSHP3QahA2QaRqMWnaY9uWDzpJKyqDkcsSK5%2BWeS8uPNvJGkf%2BEE8LXXHRGUncpancEbjnntWJHLtmRuuDmq6EWNK0m%2Byag2PuT81pvMJoz61mzUybj7xFVWoGJQKYh1KKVwHCpBVIiQtRzPhaozKsZ3PV1WpjJM0wtUiGE03NFwGmozSAjJqNqq4iOk7VIys3L0tIoetI9K47HbGkrNlhSUrDI5elQU0AlJTGIXA5PSoZLz%2BGJcn1NHLcLiIkkhUs2O9TT%2FZ%2FKPmctxjmj0Aq7Yz93PHoxqXybt1%2Fi2e7YFV6gg%2Bz9zJ%2BVXYI4TC6eWoz%2FETTsNzL%2F8AbE4jSKLaioAucc1VuLl5%2BJHLD3NHLYybE%2B0eREfeoYGLyZpkmbqs3z7ap243yirKNC8j3WZfvD81MguiOazLQ%2BaQSciq7UFDaBQA4U8ChEyY6nZrUxEZsCs%2BWbe5OMUALbt8xq4GpASbqaTSENzTWakMZmmMaAI81Gx5piG01zSKKwPNOFIY%2FNNY07AdsaSsiwoqbjI36VCapANY4FVpLgCgCpJNz6mm2z7rqLjjdzV9Bbm5i3Xojt9TTbqXNucRqNvPSoGZ6TMH%2BVMfhU%2F7x%2FvHj3qxF%2B108MvmTybUHYfxUl9siO2MYXrTJZUV8mld9ozSJINxdqt2%2FwAnNMDAvJfMmY1Ppy9SatvQroasQSTMcn3XGDWe9hLbMRwyHpioCLGc06kajcUcCmQ2OBp1UZC0tMZWu5dsZH8RqixoGT2vc1azRckfmm5qRBmo2NADc01jTAjJqJjSuUJmmueKBWKy1IKCh1MJ5piO4NNrE1DNGaQDJPu1Snn8scDmmtQK5LSck03y4v4ix%2Bhq%2FQRG8aZ%2BUHFPgjWGdJOcA8im9hFh9QjXpj%2BdV5NRLcAGkohch86QsPmx9BWpaQhAjSfNKeef4aA2L0lyemaoTuXO9upoMxIulQTy73wOlCAlhGBT7qXyrOVh1xxTGc8eta9ouIh71bKZZVqV3yKkzKkrEdKgMhp8qHcZuJpwqrASCnZpCHZpC3FILmdcy7piB0WqxPNUWi%2FBwlSA1JI%2FNJmgQ1jUZoAM0xqQxhqImgApkp4o3GV1NPJpDFzTCaYHdNTayZYgpaQyOQ%2FIaoznHaqQFTzOelMMx7A1bJGeZJ%2FeP4UxVLdc%2FjTuMkENSrGvpS5hlyBPLXzNvAp8LdW9aRDHs%2BaikPQUySOSTbHUMY3NTAug1R1mUbI4ffJpIZmx%2FMa2UbAH0oAkphaqJIXYVGQKewCUtFxCinMcCgCLfmiV%2FLiZvQUhGaT%2BtLjmi5qWlPFOBoIJM0E0iSNzUeaCh26o2NADSaiJoGGajl6UgIENKxoGOz8tMzTA7w02syhKWoKGP0qnJzVpgV2T2qIiqJG7aeBTKHVNDE0rbExuNIC%2Frix29rbRw9N%2BGPrgVUQ4SmYX1FU1FI9AFZmZqsW44qwJx1rL1lv%2BJiVHaJKmJQ21T5c%2B9XVqgJc8UwtSJI2qPNMQvambqBj1NNlakIjTlqjv5PlVBTGijmpo%2BlBoyUU5aCCXNGaRJExpmaRQE02mUNqM0hBTJOlAiunFHVqZQ5qbQI7w02sjQKCakY1ulVWqkBCahcgVS3AgaX0pnnuT2qrBuL5khP3%2FANK6nTLY2Nt5synzJgMZ7CkJ6Ioa6f3dsvsWqqX%2BWmZDlPyZqGQ0IZGvWrMfSmIfms%2BeAS3bnvtBNIaJolAIFSd6dwHryKhehMkjJpuaYCNSrSYyToKrSHmgQ6KqM8m%2BRmoKRCvJqytBTHU9aCR%2BaDUiImNMoGBptACUwmgABpknSgCEHikTrQUBNFMDujTazKClqRjTVIt1qrgVZJ1H%2FwBaq3mbqtISG8U3PNWBq%2BH7Nb25%2Bb%2FVJzIfatu%2Bm%2F0gx9qkmRm60csv%2ByKoE%2FLVGaJQfkqKSkhjY6srxQAueKrR83U%2F%2B6tICROGpH%2B9TAkibmo5eHpkkLU2gYlPFMAkOBVQnmgLjmO2Fj6Cs%2FNBaHwDnNWKQC04GgB2aCagkhJpM0ABNMJpjCmGgAFNagZXNOH3aBiU%2FpQI7YmkrMsTNLmkMpX14bcDZ981kvK7%2Feb8K0jBEsbvpOlVYLhTcZOKZR2ljb%2F2b4ekLbhNKvmEenpWcZixBPWpsZN6lbUW38n0qoG%2BSmSSg%2FIKRulAXGIeanBpWKFkPy1UVtt5IPWMUxE8Zy1JKabECPzTpzzUgV2NNzVDAU%2BgCKRqg70ASSgm3cDrtrN5oKRZjGFp9IApc07CuOzTS1QIYabmgAzTKYwzTTSGApGNAFb%2BKnsaQwWlJqgO2JpuazZQlGakDN1ZfljkHY4NZ2a1iJjaSrAOlamhWRvdQjXblF%2Bd%2BOgodhnRXtx9pS8g4yU2rWCJO9IyG3Dbqpo3zYpisWc0N0oERqeasCpuUJMcCqTnEqHsTTGW4OtMkPNFyRoOKkJ3LRoBXakzVALmgtxSGQO1ItAx8p%2FdNVdelADqKQC5opCFphNMY0mmk1ICZpM0wCmk0hiCkamIhH3qXvTGPpuaQHbNTKzLEzRQMZIAykECsGRNjkYqoCG96XcVq7CuKPnPSum0iD7Npksn%2FLZun0o20B7EKzESbqoy%2FLIfQ0zK4x2zVU8PmmMsk07qtSAwcGpkNKwyO4b2qpcf6v6U7CLULUkv3qLDI81IposIiamZpWAWkY0wIDTlNAx0%2FwDqqiH3akLhmiqsAUtFgEJptKwrjDTc07DuFJSsAU2kFwBpGp2GMHWnYpCuNY0ygo%2F%2F2QAA";
          //   String codeImg="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";
            data1=data1.replace("data:image/jpeg;base64,","");
            System.out.println(data1);
//            byte[] code = Base64.decode(codeImg,Base64.DEFAULT);
            byte[] code=decodeBase64(data1);
             Bitmap bitmap = BitmapFactory.decodeByteArray(code,0,code.length);
            System.out.println(code);
            arrayList.add(new ReclycleGetSet(bitmap,"Location : nashik\nTime : 12:22"));
            arrayList.add(new ReclycleGetSet(bitmap,"Location : nashik\nTime : 12:22"));
            arrayList.add(new ReclycleGetSet(bitmap,"Location : nashik\nTime : 12:22"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private byte[] decodeBase64(String coded){
        byte[] valueDecoded= new byte[0];
        try {
           // coded=coded.replaceAll("%2"+".+?","/");
            coded=coded.replaceAll("%2F","/");
            coded=coded.replaceAll("%2B","+");
            System.out.println(coded);
            valueDecoded = Base64.decode(coded, Base64.DEFAULT);
        } catch (Exception e) {
        }
        return valueDecoded;
    }
}