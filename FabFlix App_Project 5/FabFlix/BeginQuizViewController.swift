//
//  FirstViewController.swift
//  FabFlix
//
//  Copyright (c) 2015 FabFlix. All rights reserved.
//

import UIKit

class BeginQuizViewController: UIViewController {

    @IBOutlet weak var beginButton: UIButton!
    
    let blueColor: UIColor = UIColor(red: 13/255, green: 122/255, blue: 254/255, alpha: 1)
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        self.beginButton.layer.cornerRadius = 5
        self.beginButton.clipsToBounds = true
        self.beginButton.layer.borderWidth = 1
        
        self.beginButton.layer.borderColor = blueColor.CGColor
        self.beginButton.backgroundColor = UIColor.whiteColor()
        self.beginButton.titleLabel?.textColor = blueColor
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

}

