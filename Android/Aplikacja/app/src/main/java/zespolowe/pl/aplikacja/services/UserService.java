package zespolowe.pl.aplikacja.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import zespolowe.pl.aplikacja.model.FindFriend;
import zespolowe.pl.aplikacja.model.Friend;
import zespolowe.pl.aplikacja.model.HistoryProduct;
import zespolowe.pl.aplikacja.model.Product;
import zespolowe.pl.aplikacja.model.Receipt;
import zespolowe.pl.aplikacja.model.Respon;
import zespolowe.pl.aplikacja.model.User;

/**
 *  Klasa odpowiedzialna za obsługę API
 */

public interface UserService {

    @POST("login")//logowanie
    Call<User> loginUser(@Query("email") String email, @Query("haslo") String password);

    @POST("user/register")//rejetracja
    Call<Respon> registerUser(@Query("email") String email, @Query("haslo") String password);


    @GET("user/{id_uzytkownika}")//pobieranie znajomych poszczególne
    Call<User> getUser(@Path("id_uzytkownika") Long id);



    //pobieranie znajomych listy
    @GET("user/{id_uzytkownika}/getfriends")
    Call<List<Friend>> getFriendList(@Path("id_uzytkownika") Long id);

    //edycja profilu w ustawieniach
    @POST("user/{id_uzytkownika}/editProfile")//edycja profilu
    Call<Respon> editProfile(@Path("id_uzytkownika") Long id, @Query("name") String name, @Query("surname") String surname, @Query("image") String image, @Query("email") String email, @Query("password") String password);

    // wysyłanie skanów na serwer (pierwszw foto paragonu )
    @POST("receipt/add/{id_uzytkownika}")
    Call<Receipt> sendReceiptImagetoAPI(@Path("id_uzytkownika")Long id , @Query("file") String file);

    //pobieranie listy z paragonu
    @GET("receipt/show/{id}")
    Call<Receipt> getReceipt(@Path("id")Long id);

    //edycja paragonu
    @POST("receipt/edit/{id}")
    Call<Respon> editReceipt(@Path("id") Long id, @Query("productsUsersList") List<String> productsUsersList);

    //pobieranie listy wyszukanych userów których mozemy dodac do znajomków
    @GET("user/search/{id}/{fullname}")
    Call<List<FindFriend>> getFindFriendList(@Path("id") Long id, @Path("fullname") String fullname);

    //dodawanie znajomkow
    @GET("user/{id}/addfriend/{friendId}")
    Call<Respon> AddFriend(@Path("id") Long id,@Path("friendId") Long friendId);


    @GET("product/friendsdebts/{id}/{friendid}")
    Call<List<Product>> getFriendDebtsToMe(@Path("id") Long id,@Path("friendid") Long friendId);

    @GET("product/mydebts/{id}/{friendid}")
    Call<List<Product>> getMyDebtsToFriend(@Path("id") Long id,@Path("friendid") Long friendId);

    @GET("history/user/{id}")
    Call<List<HistoryProduct>> getHistory(@Path("id") Long id);

    @GET("receipt/getall/{id}")
    Call<List<Long>> getAllReceiptsIdList(@Path("id") Long id);

    @GET("product/payoffallfrienddebts/{id}/{friendid}")
    Call<Respon> payoffAllFriendDebts(@Path("id") Long id,@Path("friendid") Long friendId);

    @GET("user/{id}/removefriend/{friendid}")
    Call<Respon> removeFriend(@Path("id") Long id,@Path("friendid") Long friendId);
}
