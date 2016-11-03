//
//  LoadingScreenViewController.swift
//  BeakonIOS
//
//  Created by Eduardo Trejos on 11/3/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import Foundation
import UIKit


class LoadingScreenViewController: UIViewController{
    
    @IBOutlet weak var loadingImage: UIImageView!
    
    override func viewDidLoad()
    {
        super.viewDidLoad()

    }
    override func viewDidAppear(animated: Bool) {
    
        let time = dispatch_time(dispatch_time_t(DISPATCH_TIME_NOW), 2 * Int64(NSEC_PER_SEC))
        let jeremyGif = UIImage.gifImageWithName("welcomegif")
        let imageView = UIImageView(image: jeremyGif)
        
        imageView.frame = CGRect(x: 0.0, y: 0.0, width: 112.0, height: 124.0)
        imageView.center = self.view.center

        view.addSubview(imageView)
        
        
        

        dispatch_after(time, dispatch_get_main_queue()) {
            let viewControllerYouWantToPresent = self.storyboard?.instantiateViewControllerWithIdentifier("ViewController")
            self.presentViewController(viewControllerYouWantToPresent!, animated: true, completion: nil)
        }
    }
}