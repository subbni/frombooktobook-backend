package com.frombooktobook.frombooktobookbackend.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Book {

   @Id
   @Column(name="BOOK_ID")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

   @Column(nullable = false)
   private String title;
   private String author;
   private int rate;

   @Builder
    public Book(String title, String author, int rate) {
       this.title = title;
       this.author = author;
       this.rate = rate;
   }

   public Book update(String title, String author, int rate) {
      this.title = title;
      this.author = author;
      this.rate = rate;
      return this;
   }
}
