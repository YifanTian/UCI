//
//  DirectedInYearQuestion.swift
//  FabFlix
//
//  Copyright (c) 2015 FabFlix. All rights reserved.
//

import Foundation

class DirectedInYearQuestion: Question
{
    override init() {
        super.init()
        
        let movie = super.getRandomMovie()
        var answerMovie : Movie = Movie()
        var answerStar : Star = Star()

        let (result, error) = SD.executeQuery(
            "SELECT stars.first_name, stars.last_name, stars.id as starid, movies.year, " +
                "movies.id as movieid from stars, movies, stars_in_movies " +
            "WHERE stars_in_movies.star_id = stars.id AND stars_in_movies.movie_id = ? AND movies.id = ? " +
            "ORDER BY RANDOM() LIMIT 1;",
            withArgs: [movie.id, movie.id])
        
        for row in result
        {
            let starID = (row["starid"]?.asInt()!)!
            let starFirstName = (row["first_name"]?.asString()!)!
            let starLastName = (row["last_name"]?.asString()!)!
            
            answerStar = Star(id: starID, firstName: starFirstName, lastName: starLastName)
            
            let movieID = (row["movieid"]?.asInt()!)!
            let movieYear = (row["year"]?.asInt()!)!
            
            answerMovie = Movie(id: movieID, year: movieYear)
        }
        
        let (result2, error2) = SD.executeQuery(
            "SELECT movies.director from movies, stars_in_movies " +
            "WHERE stars_in_movies.movie_id=movies.id AND stars_in_movies.star_id!=? " +
            "ORDER BY RANDOM() LIMIT 3;",
            withArgs: [answerStar.id])
        
        for row in result2
        {
            allPossibleAnswers.append((row["director"]?.asString()!)!)
        }
        
        correctAnswerIndex = super.getRandomIndex()
        allPossibleAnswers.insert(movie.director, atIndex: correctAnswerIndex)
        title = "Who directed \(answerStar.fullName) in \(answerMovie.year)?"
        
    }

    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
}