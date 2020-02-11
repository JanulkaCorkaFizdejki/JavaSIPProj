package sipphone.settings;

public class InputFieldsMaxlength {

    public enum inputType {
        number,
        password,
        name
    }

    public static int getLength (inputType type) {

        int lengthOut = 0;

        switch (type) {
            case number:
                lengthOut = 20;
                break;
            case password:
                lengthOut = 100;
            case name:
                lengthOut = 100;
        }

        return  lengthOut;

    }


}
