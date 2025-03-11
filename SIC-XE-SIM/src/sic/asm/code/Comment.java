package sic.asm.code;

public class Comment extends Node {

    public Comment(String comment) {
        super(null);
        setComment(comment);
    }

    @Override
    public String toString() {
        return comment;
    }

}
