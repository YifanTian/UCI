//
//  Movie.swift
//  FabFlixQuiz
//
//  Copyright (c) 2015 James Bellamy. All rights reserved.
//

import Foundation

class Movie
{
    var id:Int
    var title:String = String()
    var year:Int
    var director = String()
    
    init(id: Int = 0, title: String = "", year: Int = 0, director: String = "")
    {
        self.id = id
        self.title = title
        self.year = year
        self.director = director
    }
    
    func describe (log : Bool = false) -> String
    {
        let result = "MOVIE: \(id) : \(title) was directed by \(director) and was released in \(year)"
        
        if (log == true)
        {
            println(result)
        }
        
        return result
    }
}