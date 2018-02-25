package com.udacity.sandwichclub.utils;

import android.support.annotation.Nullable;
import android.util.Log;
import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getName();

    @Nullable
    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject rootObject = new JSONObject(json);

            JSONObject name = (JSONObject) rootObject.get(JsonConstants.NAME);

            String mainName = name.getString(JsonConstants.MAIN_NAME);
            List<String> alsoKnowAsStrings = parseAlsoKnowAs(name);
            String placeOfOrigin = rootObject.getString(JsonConstants.PLACE_OF_ORIGIN);
            String description = rootObject.getString(JsonConstants.DESCRIPTION);
            String image = rootObject.getString(JsonConstants.IMAGE);
            List<String> ingredients = parseIngredients(rootObject);

            return new Sandwich(mainName, alsoKnowAsStrings, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            Log.e(TAG, "parseSandwichJson: ", e.getCause());
        }

        return null;
    }

    /**
     * Parses alsoKnownAs values as String from given {@link JSONObject}
     *
     * @param jsonObject root for parsing data
     * @return alsoKnownAs String list
     * @throws JSONException
     */
    private static List<String> parseAlsoKnowAs(JSONObject jsonObject) throws JSONException {
        JSONArray alsoKnownAs = jsonObject.getJSONArray(JsonConstants.ALSO_KNOWN_AS);
        final int length = alsoKnownAs.length();
        List<String> alsoKnownAsStrings = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            alsoKnownAsStrings.add(alsoKnownAs.getString(i));
        }
        return alsoKnownAsStrings;
    }

    /**
     * Parses ingredients as String from given {@link JSONObject}
     *
     * @param jsonObject root for parsing data
     * @return ingredients String list
     * @throws JSONException
     */
    private static List<String> parseIngredients(JSONObject jsonObject) throws JSONException {
        JSONArray ingredients = jsonObject.getJSONArray(JsonConstants.INGREDIENTS);
        final int length = ingredients.length();
        List<String> ingredientsStrings = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            ingredientsStrings.add(ingredients.getString(i));
        }
        return ingredientsStrings;
    }

    /**
     * Constant values for json keys
     */
    static final class JsonConstants {
        static final String NAME = "name";
        static final String MAIN_NAME = "mainName";
        static final String ALSO_KNOWN_AS = "alsoKnownAs";
        static final String PLACE_OF_ORIGIN = "placeOfOrigin";
        static final String DESCRIPTION = "description";
        static final String IMAGE = "image";
        static final String INGREDIENTS = "ingredients";
    }
}
