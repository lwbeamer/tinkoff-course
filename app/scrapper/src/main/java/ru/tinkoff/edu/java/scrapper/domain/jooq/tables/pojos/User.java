/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos;


import jakarta.validation.constraints.Size;

import java.beans.ConstructorProperties;
import java.io.Serializable;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long chatId;
    private String username;
    private String firstName;
    private String lastName;

    public User() {}

    public User(User value) {
        this.chatId = value.chatId;
        this.username = value.username;
        this.firstName = value.firstName;
        this.lastName = value.lastName;
    }

    @ConstructorProperties({ "chatId", "username", "firstName", "lastName" })
    public User(
        @NotNull Long chatId,
        @NotNull String username,
        @Nullable String firstName,
        @Nullable String lastName
    ) {
        this.chatId = chatId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Getter for <code>user.CHAT_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getChatId() {
        return this.chatId;
    }

    /**
     * Setter for <code>user.CHAT_ID</code>.
     */
    public void setChatId(@NotNull Long chatId) {
        this.chatId = chatId;
    }

    /**
     * Getter for <code>user.USERNAME</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 1000000000)
    @NotNull
    public String getUsername() {
        return this.username;
    }

    /**
     * Setter for <code>user.USERNAME</code>.
     */
    public void setUsername(@NotNull String username) {
        this.username = username;
    }

    /**
     * Getter for <code>user.FIRST_NAME</code>.
     */
    @Size(max = 1000000000)
    @Nullable
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Setter for <code>user.FIRST_NAME</code>.
     */
    public void setFirstName(@Nullable String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for <code>user.LAST_NAME</code>.
     */
    @Size(max = 1000000000)
    @Nullable
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Setter for <code>user.LAST_NAME</code>.
     */
    public void setLastName(@Nullable String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final User other = (User) obj;
        if (this.chatId == null) {
            if (other.chatId != null)
                return false;
        }
        else if (!this.chatId.equals(other.chatId))
            return false;
        if (this.username == null) {
            if (other.username != null)
                return false;
        }
        else if (!this.username.equals(other.username))
            return false;
        if (this.firstName == null) {
            if (other.firstName != null)
                return false;
        }
        else if (!this.firstName.equals(other.firstName))
            return false;
        if (this.lastName == null) {
            if (other.lastName != null)
                return false;
        }
        else if (!this.lastName.equals(other.lastName))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.chatId == null) ? 0 : this.chatId.hashCode());
        result = prime * result + ((this.username == null) ? 0 : this.username.hashCode());
        result = prime * result + ((this.firstName == null) ? 0 : this.firstName.hashCode());
        result = prime * result + ((this.lastName == null) ? 0 : this.lastName.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User (");

        sb.append(chatId);
        sb.append(", ").append(username);
        sb.append(", ").append(firstName);
        sb.append(", ").append(lastName);

        sb.append(")");
        return sb.toString();
    }
}
