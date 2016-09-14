//
//  FAQ.swift
//  BeakonIOS
//
//  Created by Alejnadra on 9/6/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class FAQ: NSObject {
    var answer = ""
    var question = ""
    
    var answerPropeties: (String) {
        get {
            return (answer)
        }
        
        set(newVal) {
            answer = newVal
        }
    }
    
    var questionPropeties: (String) {
        get {
            return (question)
        }
        
        set(newVal) {
            question = newVal
        }
    }

}
