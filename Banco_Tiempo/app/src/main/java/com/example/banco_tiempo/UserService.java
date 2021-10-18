package com.example.banco_tiempo;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;


public interface UserService {

    //POST request to login a user
    @POST("login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    // POST request to register new user
    @POST("register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

    // POST request to upload an image from storage
    @POST("uploadimage")
    Call<ImageResponse> uploadImageServer(@Body ImageRequest imageRequest);

    //POST request to upload new offer
    @POST("createservice")
    Call<NewOfferResponse> uploadNewOffer(@Body NewOfferRequest newOfferRequest);

    //POST request status Documents & Hours
    @POST("getuserstatus")
    Call<HoursDocumentResponse> checkHoursDocuments (@Body HoursDocumentsRequest hoursDocumentsRequest);

    //POST request to obtain services
    @POST("getservices")
    Call<OffersResponse> getOffers(@Body OffersRequest offersRequest);

    //POST request to obtain user services
    @POST("getuserservices")
    Call<UserOffersResponse> getUserOffers(@Body UserOffersRequest userOffersRequest);

    //POST user request a offer
    @POST("createnotification")
    Call<UserRequestOfferResponse> getUserRequestOffer(@Body UserRequestOffer userRequestOffer);

    //POST obtain user notifications
    @POST("getnotifications")
    Call<UserNotificationsResponse> getUserNotifications(@Body UserNotificationsRequest userNotificationsRequest);

    //POST obtain user accepted service
    @POST("getacceptedservices")
    Call<UserAcceptedServicesResponse> getUserAcceptedServices(@Body UserNotificationsRequest userNotificationsRequest);

    //POST to close a offer
    @POST("endservice")
    Call<EndedServiceResponse> sendEndedServiceRequest(@Body EndedServiceRequest endedServiceRequest);

    //POST to delete services
    @POST("deleteservice")
    Call<DeleteUserOfferResponse> getDelUserOffer(@Body DeleteUserOfferRequest deleteUserOfferRequest);

    //POST to update user data
    @POST("updateuser")
    Call<UpdateUserDataResponse> updateUserData(@Body UpdateUserDataRequest updateUserDataRequest);
}
