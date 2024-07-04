package com.restfulbooker.api.profile;

public enum TestProfile {
    DEV("dev"),
    QA("qa");
    private final String value;

    TestProfile(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public TestProfile fromString(String value) {
        for (TestProfile profile : TestProfile.values()) {
            if (profile.getValue().equalsIgnoreCase(value)) {
                return profile;
            }
        }
        throw new IllegalArgumentException(value + " is not a valid name for TestProfile.");
    }
}
