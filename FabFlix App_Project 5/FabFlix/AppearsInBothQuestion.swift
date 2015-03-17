//
//  AppearsInBothQuestion.swift
//  FabFlix
//
//  Copyright (c) 2015 FabFlix. All rights reserved.
//

import Foundation

class AppearsInBothQuestion: Question
{
    override init() {
        super.init()
        
        var answerStar : Star = Star()
        var movies : [Movie] = []
        
        let (result, error) = SD.executeQuery(
            "SELECT stars.id, stars.first_name, stars.last_name from stars, stars_in_movies as s, stars_in_movies as m " +
            "WHERE m.star_id = stars.id AND s.star_id = stars.id AND m.movie_id != s.movie_id " +
            "ORDER BY RANDOM() LIMIT 1;")
        
        for row in result
        {
            let id: Int = (row["id"]?.asInt()!)!
            let firstName: String = (row["first_name"]?.asString()!)!
            let lastName: String = (row["last_name"]?.asString()!)!
            
            answerStar = Star(id: id, firstName: firstName, lastName: lastName)
        }
        
        let (resultMovies, error2) = SD.executeQuery(
            "SELECT movies.title, movies.id from movies, stars_in_movies " +
            "WHERE stars_in_movies.star_id=? AND stars_in_movies.movie_id = movies.id " +
            "ORDER BY RANDOM() LIMIT 2;",
            withArgs: [answerStar.id])
        
        for row in resultMovies
        {
            let id: Int = (row["id"]?.asInt()!)!
            let title: String = (row["title"]?.asString()!)!
            
            movies.append(Movie(id: id, title: title))
        }
        
        let (wrong, error3) = SD.executeQuery(
            "SELECT stars.first_name, stars.last_name from stars, stars_in_movies " +
            "WHERE stars_in_movies.star_id = stars.id AND stars_in_movies.movie_id != ? " +
            "ORDER BY RANDOM() LIMIT 3;",
            withArgs: [movies[0].id])
        
        for row in wrong
        {
            let incorrectFirst: String = (row["first_name"]?.asString()!)!
            let incorrectLast: String = (row["last_name"]?.asString()!)!
            
            allPossibleAnswers.append("\(incorrectFirst) \(incorrectLast)")
        }
        
        correctAnswerIndex = super.getRandomIndex()
        allPossibleAnswers.insert(answerStar.fullName, atIndex: correctAnswerIndex)
        title = "Which star appears in both \(movies[0].title) and \(movies[1].title)?"
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
}