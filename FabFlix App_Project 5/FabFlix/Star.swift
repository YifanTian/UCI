//
//  Star.swift
//  FabFlixQuiz
//
//  Copyright (c) 2015 James Bellamy. All rights reserved.
//

import Foundation

class Star
{
    var id:Int
    var firstName:String = String()
    var lastName:String = String()
    
    var fullName : String
    {
        return "\(firstName) \(lastName)"
    }
    
    init(id: Int = 0, firstName: String = "", lastName: String = "")
    {
        self.id = id
        self.firstName = firstName
        self.lastName = lastName
    }
    
    func describe(log : Bool = false) -> String
    {
        let result = "STAR: \(id) : \(firstName) \(lastName)"
        
        if (log)
        {
            println(result)
        }
        
        return result
    }
}