//
//  HomeTabViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/26/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit


extension UIImage {
    
    class func imageWithColor(color: UIColor, size: CGSize) -> UIImage {
        let rect: CGRect = CGRectMake(0, 0, size.width, size.height)
        UIGraphicsBeginImageContextWithOptions(size, false, 0)
        color.setFill()
        UIRectFill(rect)
        let image: UIImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return image
    }
    
}
class HomeTabViewController: UITabBarController {

    @IBOutlet weak var openNavigation: UIBarButtonItem!
    @IBOutlet weak var tabBarHome: UITabBar!
     let button =  UIButton(type: .Custom)
    @IBOutlet weak var open: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let defaults = NSUserDefaults.standardUserDefaults()
        let points = defaults.objectForKey("points") as! Int
        let image = defaults.objectForKey("image")as! String
        let typeUser = defaults.objectForKey("socialNetworkType")as! String
        open.frame = CGRectMake(0, 0, 40, 35)
        open.layer.masksToBounds = false
        open.layer.cornerRadius = open.frame.height/2
        open.clipsToBounds = true

        if (typeUser != "localuser"){
       open.setBackgroundImage(NSURL(string: String(image)).flatMap { NSData(contentsOfURL: $0) }.flatMap { UIImage(data: $0) }!, forState: UIControlState.Normal)
        }
        else
        {
            if NSFileManager.defaultManager().fileExistsAtPath(image) {
                let url = NSURL(string: image)
                let data = NSData(contentsOfURL: url!)
                open.setBackgroundImage(UIImage(data: data!), forState: .Normal)
            }
            else{
            open.setBackgroundImage(UIImage(named: "icon_added"), forState: .Normal)
            }
        }
        // Set verde cuando es seleccionadao
        let numberOfItems = CGFloat(tabBar.items!.count)
        let tabBarItemSize = CGSize(width: tabBar.frame.width / numberOfItems, height: tabBar.frame.height)
        tabBar.selectionIndicatorImage = UIImage.imageWithColor(Constants.colors.getDarkGreen(), size: tabBarItemSize).resizableImageWithCapInsets(UIEdgeInsetsZero)
        
        
        //Swipe
        let recognizer: UISwipeGestureRecognizer = UISwipeGestureRecognizer()
        recognizer.addTarget(self, action: #selector(HomeTabViewController.swipeRight(_:)))
        recognizer.direction = .Right
        self.view .addGestureRecognizer(recognizer)
    
        //Coloca los tabs arriba
       //let yStatusBar = UIApplication.sharedApplication().statusBarFrame.size.height
        //tabBar.frame = CGRectMake(0, 0 + yStatusBar + tabBarHome.frame.size.height-15, tabBarHome.frame.size.width, tabBarHome.frame.size.height-15)
        
        //Cambia el tamaño de los tabs
        UITabBarItem.appearance().setTitleTextAttributes([NSForegroundColorAttributeName: Constants.colors.getBlack()], forState: UIControlState.Normal)
        UITabBarItem.appearance().setTitleTextAttributes([NSForegroundColorAttributeName: Constants.colors.getWhite()], forState: UIControlState.Selected)
        UITabBarItem.appearance().titlePositionAdjustment = UIOffset(horizontal: 0, vertical: -13)
    
        
        //Genera el boton de la derecha que contiene el corazon que abre la lista de deseos
       let btn1 = UIButton()
        btn1.setImage(UIImage(named: "icon_added"), forState: .Normal)
        btn1.frame = CGRectMake(0, 0, 30, 25)
        btn1.addTarget(self, action: #selector(HomeTabViewController.openWishList), forControlEvents: .TouchUpInside)
        self.navigationItem.setRightBarButtonItem(UIBarButtonItem(customView: btn1), animated: true);
        //Genera el boton del centro que contiene los puntos del usuario
       
        button.frame = CGRectMake(0, 0, 100, 40) as CGRect
        button.setTitle(String(points), forState: UIControlState.Normal)
        button.addTarget(self, action: #selector(HomeTabViewController.clickOnButton(_:)), forControlEvents: UIControlEvents.TouchUpInside)
        self.navigationItem.titleView = button
        //Observer para actualizar la tabla
        NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(HomeTabViewController.refreshPoints(_:)),name:"refreshPointsHome", object: nil)
    }
    
    func refreshPoints(notification: NSNotification){
        //load data here
        let defaults = NSUserDefaults.standardUserDefaults()
        let points = defaults.objectForKey("points") as! Int
        self.button.setTitle(String(points), forState: UIControlState.Normal)
    }
    
    func swipeRight(recognizer : UISwipeGestureRecognizer) {
        self.performSegueWithIdentifier("MenuTableViewController", sender: self)
    }
    
    //Abre el historial de puntos
    func clickOnButton(button: UIButton) {
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("HistoryPointsViewController") as! HistoryPointsViewController
        self.navigationController?.pushViewController(secondViewController, animated: true)
    }
    //Abre la lista de deseos
    func openWishList(){
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("WishViewController") as! WishViewController
        self.navigationController?.pushViewController(secondViewController, animated: true)
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
   
    func defaultMenuImage() -> UIImage {
        var defaultMenuImage = UIImage()
        
        UIGraphicsBeginImageContextWithOptions(CGSizeMake(30, 22), false, 0.0)
        
        UIColor.blackColor().setFill()
        UIBezierPath(rect: CGRectMake(0, 3, 30, 1)).fill()
        UIBezierPath(rect: CGRectMake(0, 10, 30, 1)).fill()
        UIBezierPath(rect: CGRectMake(0, 17, 30, 1)).fill()
        
        UIColor.whiteColor().setFill()
        UIBezierPath(rect: CGRectMake(0, 4, 30, 1)).fill()
        UIBezierPath(rect: CGRectMake(0, 11,  30, 1)).fill()
        UIBezierPath(rect: CGRectMake(0, 18, 30, 1)).fill()
        
        defaultMenuImage = UIGraphicsGetImageFromCurrentImageContext()
        
        UIGraphicsEndImageContext()
        
        return defaultMenuImage;
    }
   
}
