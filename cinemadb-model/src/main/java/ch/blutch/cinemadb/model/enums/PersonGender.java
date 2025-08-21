package ch.blutch.cinemadb.model.enums;

public enum PersonGender {
    NOT_DEFINED(0) {
        @Override
        public String toString() {
            return "Non défini";
        }
    },
    FEMALE(1) {
        @Override
        public String toString() {
            return "Femme";
        }
    },
    MALE(2) {
        @Override
        public String toString() {
            return "Homme";
        }
    },
    NON_BINARY(3) {
        @Override
        public String toString() {
            return "Non binaire";
        }
    };

    private final int value;

    private PersonGender(int genderNo) {
        this.value = genderNo;
    }

    public static PersonGender fromValue(int value) {
        for (PersonGender pg : PersonGender.values()) {
            if (pg.value == value) {
                return pg;
            }
        }
        return null;
    }
}
