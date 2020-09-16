package com.example.attaurrahman.e_complaint.networking;


import com.example.attaurrahman.e_complaint.dataModel.ChangePasswordResponse;
import com.example.attaurrahman.e_complaint.dataModel.ForgotPasswordResponse;
import com.example.attaurrahman.e_complaint.dataModel.ForgotVerifyResponse;
import com.example.attaurrahman.e_complaint.dataModel.loginModel.LogInResponse;
import com.example.attaurrahman.e_complaint.dataModel.signUpModel.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface

APIServices {


    @FormUrlEncoded
    @POST("login")
    Call<LogInResponse> logIn(@Field("email") String email,
                              @Field("password") String password);


    @FormUrlEncoded
    @POST("signup")
    Call<SignUpResponse> signUp(@Field("FullName") String fullName,
                                @Field("email") String email,
                                @Field("password") String password,
                                @Field("retype_password") String retypePassword);

    @FormUrlEncoded
    @POST("reset")
    Call<ForgotPasswordResponse> forgotPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("checkCode")
    Call<ForgotVerifyResponse> forgotVerify(@Field("email") String email,
                                            @Field("code") String checkCode);

    @FormUrlEncoded
    @POST("ChangePassword")
    Call<ChangePasswordResponse> changePassword(@Field("email") String email,
                                                @Field("newPassword") String newPassword);
//
//
//    @Multipart
//    @POST("imagesuploading")
//    Call<UploadImageResponseModel> uploadMultipleImages(
//            @Part("email") RequestBody email,
//            @Part MultipartBody.Part[] photo,
//            @Part("photos[]") RequestBody fileName);
//
//
//    @FormUrlEncoded
//    @POST("reset")
//    Call<VerifyEmailResponseModel> sendCodeToEmail(@Field("email") String email);
//
//
//    @FormUrlEncoded
//    @POST("checkCode")
//    Call<VerifyCodeResponseModel> checkCode(@Field("email") String email,
//                                            @Field("code") String code);


//    @Part("location") RequestBody location,
//    @Part("users[]") ArrayList<String> title,










  /*  @FormUrlEncoded
    @POST("login")
    Call<LoginResponseModel> addUser(@Field("email") String email,
                                       @Field("password") String password,
                                       @Field("deviceToken") String deviceToken);


    @Multipart
    @POST("register")
    Call<SignUpResponseModel> userSignUp(@Part("name") RequestBody name,
                                         @Part("email") RequestBody email,
                                         @Part("password") RequestBody password,
                                         @Part("confirmPassword") RequestBody passwordConfirmation,
                                         @Part("phoneNumber") RequestBody phoneNumber,
                                         @Part("deviceType") RequestBody deviceType,
                                         @Part("deviceToken") RequestBody deviceToken,
                                         @Part("planId") RequestBody planId,
                                         @Part("adults") RequestBody adults,
                                         @Part("kidsUnder14") RequestBody kidsUder14,
                                         @Part MultipartBody.Part photo,
                                         @Part("profilePicture") RequestBody fileName);

    @GET("all-recipies")
    Call<GetAllRecipeResponseModel> allRecepies();


    @GET("recipe-detail?")
    Call<RecipeDetailResponseModel> recipeDetail(@Query("recipeId") int id);


    @GET("user-profile")
    Call<GetProfileResponseModel> profile();


    @GET("get-favouite-recipies")
    Call<GetFavouriteResponseModel> favourite();


    @FormUrlEncoded
    @POST("update-favouite-recipe-status")
    Call<BaseResponse> favoriteRecipe(@Field("recipeId") String recipeId,
                                      @Field("favouriteStatus") String favoriteStatus);


    @Multipart
    @POST("update-profile-picture")
    Call<ProfileUpdateResponseModel> updateProfilePic(@Part MultipartBody.Part photo,
                                                      @Part("profilePicture") RequestBody fileName);


    @FormUrlEncoded
    @POST("update-profile")
    Call<ProfileUpdateResponseModel> updateProfile(@Field("name") String name,
                                                   @Field("phoneNumber") String phoneNumber,
                                                   @Field("location") String location,
                                                   @Field("postalCode") String postalCode,
                                                   @Field("gender") String gender,
                                                   @Field("adults") String adults,
                                                   @Field("kidsUnder14") String kidsUnder14);


    @FormUrlEncoded
    @POST("update-profile")
    Call<ProfileUpdateResponseModel> updateProfilePlanId(@Field("name") String name,
                                                         @Field("location") String location,
                                                         @Field("postalCode") String postalCode,
                                                         @Field("gender") String gender,
                                                         @Field("adults") String adults,
                                                         @Field("kidsUnder14") String kidsUnder14,
                                                         @Field("planId") String planId);


    @FormUrlEncoded
    @POST("reset-password")
    Call<ForgotPasswordResponseModel> forgotPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("reset-password-verify")
    Call<VerifyEmailResponseModel> sendCodeToEmail(@Field("email") String email,
                                             @Field("code") String code);


    @FormUrlEncoded
    @POST("change-password")
    Call<ChangePasswordResponseModel> changePassword(@Field("newPassword") String newPassword,
                                                     @Field("confirmPassword") String confirmPassword);


    @FormUrlEncoded
    @POST("update-notification-status")
    Call<NotificationResponseModel> notificationStatus(@Field("status") String notificationStatus);


    @POST("deactive-account")
    Call<DeactiveResponseModel> deactiveAccount();


    @FormUrlEncoded
    @POST("contact-us")
    Call<ContactUsResponseModel> contactUs(@Field("description") String description);


    @GET("get-super-markets")
    Call<GetSuperMarketResponseModel> getSuperMarket();


    @GET("recipe-ingredients?")
    Call<GetSpecificRecipeIngredientsResponseModel> specificRecipeIngredients(@Query("recipeId") int id);


    @FormUrlEncoded
    @POST("shoping")
    Call<ShoppingResponseModel> shopping(@Field("superMarket") String superMarket,
                                         @Field("postalCode") String strPostalCode,
                                         @Field("shippingAddress") String shoppingAddress,
                                         @Field("items") String items);


    @FormUrlEncoded
    @POST("rating")
    Call<UpdateRecipeRatingResponseModel> updateRecipeRating(@Field("recipeId") int recipeId,
                                                             @Field("rating") float rating);


    @GET("orders")
    Call<GetOrderResponseModel> getMyOrder();


    @GET("ingredients-list")
    Call<GetAllIngredientsWithOutCategoryResponseModel> getSpecificIngredientsWithOutCategory();


    @GET("ingredients-list?")
    Call<GetSpecificIngredientsWithOutCategoryResponseModel> specificRecipeIngredientsWithOutCategory(@Query("recipeId") int id);


    @FormUrlEncoded
    @POST("swipe-or-delete-recipe")
    Call<SwipeRecipeResponseModel> swipeOrDelete(@Field("recipeId") int recipeId, @Field("type") String swipeType);




      @Multipart
    @POST("usergeneration")
    Call<RegistrationResponseModel> userSignUp(@Part("email") RequestBody email,
                                               @Part("password") RequestBody password,
                                               @Part("device") RequestBody deviceType,
                                               @Part("device_id") RequestBody deviceId,
                                               @Part("type") RequestBody userType,
                                               @Part("gender") RequestBody gender,
                                               @Part("name") RequestBody name,
                                               @Part("interests") RequestBody interests,
                                               @Part("location") RequestBody location,
                                               @Part("distance") RequestBody distance,
                                               @Part("latitude") RequestBody lat,
                                               @Part("longitude") RequestBody lon,
                                               @Part("language") RequestBody language,
                                               @Part("notifications") RequestBody notifications,
                                               @Part MultipartBody.Part photo,
                                               @Part("image") RequestBody fileName);



 RequestBody latBody = RequestBody.create(MediaType.parse("multipart/form-data"), "1234");
        RequestBody lonBody = RequestBody.create(MediaType.parse("multipart/form-data"), "5678");
        RequestBody interestsBody = RequestBody.create(MediaType.parse("multipart/form-data"), "testing");
        RequestBody locationBody = RequestBody.create(MediaType.parse("multipart/form-data"), "testLocation");
        RequestBody distanceBody = RequestBody.create(MediaType.parse("multipart/form-data"), "123");
        RequestBody notificationBody = RequestBody.create(MediaType.parse("multipart/form-data"), "0");
        RequestBody languageBody = RequestBody.create(MediaType.parse("multipart/form-data"), "English");


*/

}
