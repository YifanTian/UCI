//
//  Question.swift
//  FabFlix
//
//  Copyright (c) 2015 FabFlix. All rights reserved.
//

import Foundation

class Question: NSObject, QuestionTemplate, NSCoding
{
    var title: String = ""
    var allPossibleAnswers: [String] = []
    var correctAnswerIndex: Int = 0
    
    override init()
    {
        
    }
    
    func encodeWithCoder(aCoder: NSCoder)
    {
        aCoder.encodeObject(title, forKey: "title")
        aCoder.encodeObject(allPossibleAnswers, forKey: "allPossibleAnswers")
        aCoder.encodeObject(correctAnswerIndex, forKey: "correctAnswerIndex")
    }
    
    required init(coder aDecoder: NSCoder)
    {
        self.title = aDecoder.decodeObjectForKey("title") as String
        self.allPossibleAnswers = aDecoder.decodeObjectForKey("allPossibleAnswers") as [String]
        self.correctAnswerIndex = aDecoder.decodeObjectForKey("correctAnswerIndex") as Int
    }

    func getRandomMovie() -> Movie
    {
        let (result, error) = SD.executeQuery(
            "SELECT id, title, year, director FROM movies " +
                "WHERE (SELECT count(stars_in_movies.star_id) FROM stars_in_movies WHERE stars_in_movies.movie_id=movies.id) >= 1 " +
            "ORDER BY RANDOM() LIMIT 1;")
        
        var movie : Movie = Movie()
        
        for row in result
        {
            let id: Int = (row["id"]?.asInt()!)!
            let title: String = (row["title"]?.asString()!)!
            let year: Int = (row["year"]?.asInt()!)!
            let director: String = (row["director"]?.asString()!)!
            
            movie = Movie(id: id, title: title, year: year, director: director)
        }
        
        return movie
    }
    
    func getRandomMovieWithMinStars(numStars: Int) -> Movie
    {
        let (result, error) = SD.executeQuery(
            "SELECT id, title, year, director FROM movies " +
                "WHERE (SELECT count(stars_in_movies.star_id) FROM stars_in_movies WHERE stars_in_movies.movie_id=movies.id) >= ? " +
            "ORDER BY RANDOM() LIMIT 1;",
            withArgs: [numStars])
        
        var movie : Movie = Movie()
        
        for row in result
        {
            let id: Int = (row["id"]?.asInt()!)!
            let title: String = (row["title"]?.asString()!)!
            let year: Int = (row["year"]?.asInt()!)!
            let director: String = (row["director"]?.asString()!)!
            
            movie = Movie(id: id, title: title, year: year, director: director)
        }
        
        return movie
    }
    
    func getRandomIndex() -> Int
    {
        return Int(arc4random_uniform(4))
    }
    
    func printall()
    {
        println(title)
        println(allPossibleAnswers)
        println(correctAnswerIndex)
    }
}