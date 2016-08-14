package com.crrc.babymap.app.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;
import java.util.Set;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by carlos on 19/07/2016.
 */
public class DialogUtil {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showErrorToast(Context context, Throwable e) {
        String message = e.getMessage();
        try {
            if (e instanceof HttpException) {
                HttpException er = (HttpException) e;
                String errorBody = er.response().errorBody().string();
                JsonObject jsonObject = new JsonParser().parse(errorBody).getAsJsonObject();
                message = er.message();
                message = jsonObject.get("message").getAsString();

                JsonObject children = jsonObject.getAsJsonObject("errors").getAsJsonObject("children");
                Set<Map.Entry<String, JsonElement>> entries = children.entrySet();

                if (entries.size() > 0) {
                    message += "\n";
                }

                for (Map.Entry<String, JsonElement> entry : entries) {
                    try {
                        JsonArray errors = entry.getValue().getAsJsonObject().getAsJsonArray("errors");

                        for (JsonElement element : errors) {

                            message += "\n" + element;

                        }

                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        message = message.replaceAll("\\[", "");
        message = message.replaceAll("\\]", "");
        message = message.replaceAll("\"", "");
        message = message.replaceAll("null", "");

        showToast(context, message);
    }

    public static void showAlert(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", null);
        builder.show();
    }
}