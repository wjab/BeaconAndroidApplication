//
//  Category.swift
//  BeakonIOS
//
//  Created by Christopher on 9/2/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class Category: NSObject {
    var type = ""
    var id = ""
    var descriptionCategory = ""
    var imageUrl = ""
    
    var idPropeties: (String) {
        get {
            return (id)
        }
        
        set(newVal) {
            id = newVal
        }
    }
    
    var typePropeties: (String) {
        get {
            return (type)
        }
        
        set(newVal) {
           type = newVal
        }
    }
    
    var descriptionPropeties: (String) {
        get {
            return (descriptionCategory)
        }
        
        set(newVal) {
            descriptionCategory = newVal
        }
    }
    
    var imageUrlPropeties: (String) {
        get {
            return (imageUrl)
        }
        
        set(newVal) {
            imageUrl = newVal
        }
    }

}
