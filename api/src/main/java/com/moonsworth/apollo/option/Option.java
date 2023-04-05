package com.moonsworth.apollo.option;

import io.leangen.geantyref.TypeToken;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static java.util.Objects.requireNonNull;

/**
 * Represents an option.
 *
 * @param <V> the value type
 * @param <M> the option builder type
 * @param <I> the option type
 * @since 1.0.0
 */
@Getter
@EqualsAndHashCode
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public abstract class Option<V, M extends OptionBuilder<V, M, I>, I extends Option<V, M, I>> {

    /**
     * Returns a new {@link SimpleOption.SimpleOptionBuilder}.
     *
     * @param <V> the value type
     * @return a new simple option builder
     * @since 1.0.0
     */
    public static <V> SimpleOption.SimpleOptionBuilder<V> builder() {
        return new SimpleOption.SimpleOptionBuilder<>();
    }

    /**
     * Returns a new {@link NumberOption.NumberOptionBuilder}.
     *
     * @param <V> the value type
     * @return a new number option builder
     * @since 1.0.0
     */
    public static <V extends Number & Comparable<V>> NumberOption.NumberOptionBuilder<V> number() {
        return new NumberOption.NumberOptionBuilder<>();
    }

    public static <T> ListOption.ListOptionBuilder<T> list() {
        return new ListOption.ListOptionBuilder<>();
    }

    /**
     * Returns the option node.
     *
     * @return the node string array
     * @since 1.0.0
     */
    String[] node;

    /**
     * Returns the option {@link TypeToken} for {@code T}.
     *
     * @return the value type token
     * @since 1.0.0
     */
    TypeToken<V> typeToken;

    /**
     * Returns the option {@link String} comment.
     *
     * @return the option comment
     * @since 1.0.0
     */
    String comment;

    /**
     * Returns the option {@code T} default value.
     *
     * @return the option default value
     * @since 1.0.0
     */
    V defaultValue;

    /**
     * Returns {@code true} if this option should be aware to the client,
     * otherwise returns {@code false}.
     *
     * @return whether the option should be aware to the client
     * @since 1.0.0
     */
    boolean notify;

    Option(M builder) {
        this.node = requireNonNull(builder.node, "node");
        this.typeToken = requireNonNull(builder.typeToken, "typeToken");

        this.comment = builder.comment;
        this.defaultValue = builder.defaultValue;
        this.notify = builder.notify;
    }

    /**
     * Returns the {@link Option#getNode()} as a joined {@link String}.
     *
     * @return a joined node string
     * @since 1.0.0
     */
    public String getKey() {
        return String.join(".", this.getNode());
    }

}
