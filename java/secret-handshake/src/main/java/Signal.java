enum Signal {

    WINK(1),
    DOUBLE_BLINK(2),
    CLOSE_YOUR_EYES(3),
    JUMP(4);


    private final int value;

    Signal(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }
}
