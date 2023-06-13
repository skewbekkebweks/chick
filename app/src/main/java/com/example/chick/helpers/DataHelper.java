package com.example.chick.helpers;

import static com.example.chick.helpers.PreferencesHelper.saveToken;

import android.content.Context;
import android.util.Pair;
import android.widget.Toast;

import com.example.chick.R;
import com.example.chick.models.Category;
import com.example.chick.models.Course;
import com.example.chick.models.Exercise;
import com.example.chick.models.OrderFoodset;
import com.example.chick.server.JwtRequest;
import com.example.chick.server.JwtResponse;
import com.example.chick.models.Order;
import com.example.chick.models.User;
import com.example.chick.models.UserCourse;
import com.example.chick.server.CategoryService;
import com.example.chick.server.CourseService;
import com.example.chick.server.ExerciseService;
import com.example.chick.server.OrderRequest;
import com.example.chick.server.OrderService;
import com.example.chick.server.UserCategoryService;
import com.example.chick.server.UserCourseService;
import com.example.chick.server.UserExerciseService;
import com.example.chick.server.UserService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DataHelper {
    private static final double RPS = (double) 1 / 10;
    private static final double USER_RPS = (double) 1 / 1000;
    private static User user = null;
    private static LocalDateTime userLoading = null;
    private static ArrayList<Course> courses = null;
    private static LocalDateTime coursesLoading = null;
    private static ArrayList<Exercise> exercises = null;
    private static LocalDateTime exercisesLoading = null;
    private static ArrayList<Category> categories = null;
    private static LocalDateTime categoriesLoading = null;

    private static final Retrofit retrofit = RetroHelper.getServer();
    private static final UserService userService = retrofit.create(UserService.class);
    private static final UserExerciseService userExerciseService = retrofit.create(UserExerciseService.class);
    private static final UserCategoryService userCategoryService = retrofit.create(UserCategoryService.class);
    private static final UserCourseService userCourseService = retrofit.create(UserCourseService.class);
    private static final CourseService courseService = retrofit.create(CourseService.class);
    private static final ExerciseService exerciseService = retrofit.create(ExerciseService.class);
    private static final CategoryService categoryService = retrofit.create(CategoryService.class);
    private static final OrderService orderService = retrofit.create(OrderService.class);

    public static boolean canRequest(LocalDateTime last) {
        return DataHelper.canRequest(last, RPS);
    }
    public static boolean canRequest(LocalDateTime last, double rps) {
        return Duration.between(LocalDateTime.now(), last).getSeconds() * rps > 1;
    }
    @SafeVarargs
    public static void login(Context context, String email, String password, Callable<Void>... functions) {
        JwtRequest jwtRequest = new JwtRequest(email, password);
        Call<JwtResponse> call = userService.getUserByEmailAndPassword(jwtRequest);
        call.enqueue((ChickCallback<JwtResponse>) (call1, response) -> {
            JwtResponse jwtResponse = response.body();
            if (jwtResponse == null) {
                Toast.makeText(context, context.getResources().getString(R.string.incorrect_login_or_password), Toast.LENGTH_SHORT).show();
            } else {
                saveToken(jwtResponse.getAccessToken());
                getUserByToken(functions);
            }
        });
    }

    @SafeVarargs
    public static void getUserByToken(Callable<Void>... functions) {
        getUserByToken(false, functions);
    }

    @SafeVarargs
    public static void getUserByToken(boolean isForced, Callable<Void>... functions) {
        String token = PreferencesHelper.getToken();
        if (!isForced && user != null && !DataHelper.canRequest(userLoading,USER_RPS) || token == null) {
            CallHelper.callFunctions(functions);
            return;
        }
        userService.getUserByToken().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                userLoading = LocalDateTime.now();
                CallHelper.callFunctions(functions);
//                DateTimeHelper.correctInputData();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
                CallHelper.callFunctions(functions);
            }
        });
    }

    @SafeVarargs
    public static void loadCourses(Callable<Void>... functions) {
        loadCourses(false, functions);
    }

    @SafeVarargs
    public static void loadCourses(boolean isForced, Callable<Void>... functions) {
        if (!isForced && courses != null && !canRequest(coursesLoading)) {
            CallHelper.callFunctions(functions);
            return;
        }
        Call<List<Course>> call = courseService.getCourses();
        call.enqueue((ChickCallback<List<Course>>) (call1, response) -> {
            courses = (ArrayList<Course>) response.body();
            coursesLoading = LocalDateTime.now();

            CallHelper.callFunctions(functions);
        });
    }

    @SafeVarargs
    public static void loadExercises(Callable<Void>... functions) {
        loadExercises(false, functions);
    }

    @SafeVarargs
    public static void loadExercises(boolean isForced, Callable<Void>... functions) {
        if (!isForced && exercises != null && !canRequest(exercisesLoading)) {
            CallHelper.callFunctions(functions);
            return;
        }
        Call<List<Exercise>> call = exerciseService.getExercises();
        call.enqueue((ChickCallback<List<Exercise>>) (call1, response) -> {
            exercises = (ArrayList<Exercise>) response.body();
            exercisesLoading = LocalDateTime.now();

            CallHelper.callFunctions(functions);
        });
    }

    @SafeVarargs
    public static void loadCategories(Callable<Void>... functions) {
        loadCategories(false, functions);
    }

    @SafeVarargs
    public static void loadCategories(boolean isForced, Callable<Void>... functions) {
        if (!isForced && categories != null && canRequest(categoriesLoading)) {
            CallHelper.callFunctions(functions);
            return;
        }
        Call<List<Category>> call = categoryService.getCategories();
        call.enqueue((ChickCallback<List<Category>>) (call1, response) -> {
            categories = (ArrayList<Category>) response.body();
            categoriesLoading = LocalDateTime.now();

            CallHelper.callFunctions(functions);
        });
    }

    public static void saveUser(Callable<Void>... functions) {
        Call<Void> call = userService.saveUser(user);
        call.enqueue(new ChickCallback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                CallHelper.callFunctions(functions);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ChickCallback.super.onFailure(call, t);
                t.printStackTrace();
                CallHelper.callFunctions(functions);
            }
        });
    }

    public static void saveOrder(Order order) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setFoodsetsId(order.getOrderFoodsets().stream().map(orderFoodset -> orderFoodset.getFoodset().getId())
                .collect(Collectors
                        .toCollection(ArrayList::new)));
        orderRequest.setStore(order.getStore());
        Call<Order> call = orderService.saveOrder(orderRequest);
        call.enqueue((ChickCallback<Order>) (call1, response) -> {
//            DateTimeHelper.correctDate(response.body().getDate());
            user.getOrders().add(response.body());
        });
    }

    public static void changePassword(String oldPassword, String newPassword, Callable<Void>... func) {
        Call<Void> call = userService.changePassword(new Pair<>(oldPassword, newPassword));
        call.enqueue((ChickCallback<Void>) (call1, response) -> {
            CallHelper.callFunctions(func);
        });
    }

    public static void forgotPassword(String email, Callable<Void>... func) {
        Call<Void> call = userService.forgotPassword(email);
        call.enqueue((ChickCallback<Void>) (call1, response) -> {
            CallHelper.callFunctions(func);
        });
    }

    public static void postUserExercise(Exercise exercise) {
        Call<Void> call;
        if (user.getExercises().contains(exercise)) {
            call = userExerciseService.deleteUserExercise(exercise.getId());
            user.getExercises().remove(exercise);
        } else {
            call = userExerciseService.saveUserExercise(exercise.getId());
            user.getExercises().add(exercise);
        }
        call.enqueue((ChickCallback<Void>) (call1, response) -> {
        });
    }

    public static void postUserCategory(Category category) {
        Call<Void> call;
        if (user.getCategories().contains(category)) {
            call = userCategoryService.deleteUserCategory(category.getId());
            user.getCategories().remove(category);
        } else {
            call = userCategoryService.saveUserCategory(category.getId());
            user.getCategories().add(category);
        }
        call.enqueue((ChickCallback<Void>) (call1, response) -> {
        });
    }

    public static void saveUserCourse(Course course) {
        Call<UserCourse> call = userCourseService.saveUserCourse(course.getId());
        call.enqueue((ChickCallback<UserCourse>) (call1, response) -> {
//            DateTimeHelper.correctDate(response.body().getStartDate());
            user.getUserCourses().add(response.body());
        });
    }

    @SafeVarargs
    public static void saveNewUser(User user, String password, Context context, Callable<Void>... functions) {
        Call<JwtResponse> call = userService.saveNewUser(new Pair<>(user, password));
        call.enqueue((ChickCallback<JwtResponse>) (call1, response) -> {
            JwtResponse res = response.body();
            if (res == null || res.getAccessToken() == null) {
                Toast.makeText(context, context.getResources().getString(R.string.error_ccured), Toast.LENGTH_SHORT).show();
                return;
            }
            saveToken(res.getAccessToken());
            getUserByToken(functions);
        });
    }

    public static User getUser() {
        return user;
    }

    public static void setUserNull() {
        user = null;
    }

    public static ArrayList<Course> getCourses() {
        return courses;
    }

    public static ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public static ArrayList<Category> getCategories() {
        return categories;
    }
}
