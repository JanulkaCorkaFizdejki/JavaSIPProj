package sipphone.viewControllers;

public class TextFieldsLength {

    public enum typeControl {
        pass,
        phone,
        name
    }

    public static int maxlength (typeControl typeName) {
            int opt_maxLength = 0;

            switch (typeName) {
                case name:
                    opt_maxLength = 100;
                    break;
                case pass:
                    opt_maxLength = 50;
                    break;
                case phone:
                    opt_maxLength = 30;
                    break;
            }

        return opt_maxLength;
    }
}
