//
//  WishViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/25/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON

class WishViewController:UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    var wishArray: [Wish] = []
    var actualyArrayIndex = 0
    var wishCount = 1
     let btn1 = UIButton()
    @IBOutlet weak var table: UITableView!
    let cellReuseIdentifier = "cellWish"
    let defaults = NSUserDefaults.standardUserDefaults()
    var idUser = ""
        override func viewDidLoad() {
        super.viewDidLoad()
      //  addSlideMenuButton()
        self.idUser = (defaults.objectForKey("userId") as? String)!
        service()
        table.delegate = self
        table.dataSource = self
                NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(WishViewController.refreshWishCount),name:"refreshWishCount", object: nil)
            NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(WishViewController.loadList(_:)),name:"wish", object: nil)
            let points = defaults.objectForKey("points") as! Int
             self.wishCount = defaults.objectForKey("wishCount")as!Int
            //Genera el boton de la derecha que contiene el corazon que abre la lista de deseos
           
            btn1.setBackgroundImage(UIImage(named: "icon_added"), forState: .Normal)
            btn1.setTitle(String(wishCount), forState: .Normal)
            btn1.setTitleColor(UIColor.blackColor(), forState: .Normal)
            btn1.frame = CGRectMake(0, 0, 30, 25)
            btn1.addTarget(self, action: #selector(WishViewController.openWishList), forControlEvents: .TouchUpInside)
            self.navigationItem.setRightBarButtonItem(UIBarButtonItem(customView: btn1), animated: true);
            
            //Button abre  menu
            var open = UIButton()
            let image = defaults.objectForKey("image")as! String
            let typeUser = defaults.objectForKey("socialNetworkType")as! String
            open = Utils.loadMenuButton(open, image: image, typeUser: typeUser)
            open.frame = CGRectMake(0, 0, 40, 35)
            open.layer.masksToBounds = false
            open.layer.cornerRadius = open.frame.height/2
            open.clipsToBounds = true
            open.addTarget(self, action: #selector(WishViewController.openMenu), forControlEvents: .TouchUpInside)
            self.navigationItem.setLeftBarButtonItem(UIBarButtonItem(customView: open), animated: true)
            
            //Genera el boton del centro que contiene los puntos del usuario
            let button =  UIButton(type: .Custom)
            button.frame = CGRectMake(0, 0, 100, 40) as CGRect
            button.setTitle(String(points), forState: UIControlState.Normal)
            button.addTarget(self, action: #selector(WishViewController.clickOnButton(_:)), forControlEvents: UIControlEvents.TouchUpInside)
            self.navigationItem.titleView = button
            
    }
    
    func refreshWishCount(){
        //Refresca el contador de wish al eliminar un producto de la lista
        var wish = self.wishCount
        wish = wish - 1
        self.wishCount = wish
        defaults.setObject(self.wishCount, forKey: "wishCount")
        btn1.setTitle(String(wishCount), forState: .Normal)

    }
    
    //Abre el menu
    func openMenu(){
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("MenuContainerViewController") as! MenuContainerViewController
        self.navigationController?.pushViewController(secondViewController, animated: true)
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
    
    func loadList(notification: NSNotification){
        service()
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func service(){
        //Endpoint
        self.wishArray = []
        let url : String = "http://buserdevel.cfapps.io/user/id/"+self.idUser
        //Crea el request
        print(url)
        Alamofire.request(.GET, url, encoding: .JSON)
            .responseJSON
            {
                response in switch response.result
                {
                //Si la respuesta es satisfactoria
                case .Success(let JSON):
                    let response = JSON as! NSDictionary
                     var user = JSON as! NSDictionary
                    //Si la respuesta no tiene status 404
                    if((response)["status"] as! Int != 404)
                    {
                        print((response)["status"])
                        user = response.objectForKey("user")! as! NSDictionary
                        let productList = user.mutableArrayValueForKey("productWishList")
                        for (indexP, product) in productList.enumerate()
                        {
                            print(indexP, ":", product)
                            let wishObject = Wish()
                            wishObject.productIdPropeties = product.objectForKey("productId") as! String
                            wishObject.productNamePropeties = product.objectForKey("productName") as! String
                            wishObject.pricePropeties = product.objectForKey("price") as! Int
                            wishObject.imageUrlListPropeties = product.objectForKey("imageUrlList") as! String
                            //PointsByPrice anadir
                            //productObject.pointsByPricePropeties = product.objectForKey("pointsByPrice") as! Int
                            self.wishArray.append(wishObject)
                        }
                        self.table.reloadData()
                    }
                    else
                    {
                        print("Hubo un error obteniendo los datos de lista de deseos")
                    }
                case .Failure(let error):
                    print("Hubo un error realizando la peticion: \(error)")
                }
        }
    }

    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.wishArray.count
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell:WishCell = self.table.dequeueReusableCellWithIdentifier(cellReuseIdentifier) as! WishCell!
        let wishObject = self.wishArray[indexPath.row]
        let name = wishObject.productNamePropeties
        let urlImage = wishObject.imageUrlListPropeties
        cell.configure(name,urlImage: urlImage, product: wishObject)
        return cell
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        self.actualyArrayIndex = indexPath.row
       // let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("DetailPromoViewController") as! DetailPromoViewController
       // secondViewController.toPass = self.wishArray[self.actualyArrayIndex]
       // self.navigationController?.pushViewController(secondViewController, animated: true)
        
        
    }

  
}

