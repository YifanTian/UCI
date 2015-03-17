//
//  AppearTogetherQuestion.swift
//  FabFlix
//
//  Copyright (c) 2015 FabFlix. All rights reserved.
//

import Foundation

class AppearTogetherQuestion: Question
{
    override init() {
        super.init()
      
        var movie: Movie = super.getRandomMovieWithMinStars(3)
        var answerStars : [Star] = []
        
        let (result, err) = SD.executeQuery(
            "SELECT stars.first_name, stars.last_name, stars.id FROM stars, stars_in_movies " +
            "WHERE stars_in_movies.movie_id=? AND stars_in_movies.star_id=stars.id " +
            "ORDER BY RANDOM() LIMIT 2;", withArgs: [movie.id])
        
        for row in result
        {
            let id: Int = (row["id"]?.asInt()!)!
            let firstName: String = (row["first_name"]?.asString()!)!
            let lastName: String = (row["last_name"]?.asString()!)!
            
            answerStars.append(Star(id: id, firstName: firstName, lastName: lastName))
        }
        
        let (wrongResults, err2) = SD.executeQuery(
            "SELECT title, movies.id AS m FROM movies " +
            "WHERE NOT EXISTS(SELECT star_id FROM stars_in_movies WHERE star_id=? AND movie_id=m) " +
            "ORDER BY RANDOM() LIMIT 3;",
            withArgs: [answerStars[0].id])
        
        for row in wrongResults
        {
            let wrongTitle = (row["title"]?.asString()!)!
            
            allPossibleAnswers.append("\(wrongTitle)")
        }
        
        correctAnswerIndex = super.getRandomIndex()
        allPossibleAnswers.insert(movie.title, atIndex: correctAnswerIndex)
        title = "In which movie do stars \(answerStars[0].fullName) and \(answerStars[1].fullName) appear in together?"
    }
    
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
}