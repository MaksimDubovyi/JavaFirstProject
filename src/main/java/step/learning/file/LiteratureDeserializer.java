package step.learning.file;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import step.learning.oop.*;

import java.lang.reflect.Type;

public class LiteratureDeserializer implements JsonDeserializer<Literature> {
    /**
     *забезпечує правильну десеріалізацію елементів JSON на об'єкти типу Література або її підкласи відповідно до значення поля «тип» у даних JSON.
     * @param json - JSON елемент, який слід дезеріалізувати в об'єкт типу Література або його підкласи.
     * @param typeOfT  - тип об'єкта, для якого виконується дезеріалізація (в даному випадку Literature.class або його підкласи).
     * @param context контекст є контекстом дезеріалізації, який забезпечує доступ до методів Gson для обробки десеріалізації.
     * @return
     * @throws JsonParseException
     */
    @Override
    public Literature deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException
    {
        if (json == null || json.isJsonNull()) {// - Перевіряє, чи є елемент JSON нульовим або JSON null, а потім
            return null;                        // повертає null, щоб уникнути помилок у десеріалізації, якщо немає даних.
        }

        JsonElement typeElement = json.getAsJsonObject().get("type");  //- отримує елемент JSON з назвою «тип», який містить інформацію про тип літератури.
                                                                       // Далі перевіряється поле «тип»:
        if (typeElement == null || typeElement.isJsonNull()) {
            throw new JsonParseException("Поле 'type' відсутнє чи має значення null");
        }

        String type = typeElement.getAsString(); //Залежно від значення створюється і повертається об'єкт відповідного підкласу «Література»:

        if ("Book".equals(type)) {                             //Якщо «тип» - «Книга», створюється об'єкт «Книга».
            return context.deserialize(json, Book.class);
        } else if ("Journal".equals(type)) {                   //Якщо «тип» - «Журнал», створюється об'єкт «Журнал».
            return context.deserialize(json, Journal.class);
        } else if ("Newspaper".equals(type)) {                 //Якщо «тип» - «Газета», створюється об'єкт «Газета».
            return context.deserialize(json, Newspaper.class);
        } else if ("Hologram".equals(type)) {                  //Якщо «тип» - «Голограма», створюється об'єкт класу Голограма.
            return context.deserialize(json, Hologram.class);
        } else if ("Poster".equals(type)) {                    //Якщо «тип» - «Плакат», створюється об'єкт «Плакат».
            return context.deserialize(json, Poster.class);
        } else {
            throw new JsonParseException("Невідомий тип літератури: " + type);  //Якщо значення "type" не таке, як будь-яке з наведених вище,
        }                                                                       // виняток JsonParseException відкидається з повідомленням
    }                                                                           //"Nevidomium lIteraturi type:" + type ", вказуючи невідомий тип літератури.
}
