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
    var navigationStatus = 1
    var wishArray: [Wish] = []
    var actualyArrayIndex = 0
    var wishCount = 1
     var btn1 = UIButton()
    @IBOutlet weak var table: UITableView!
    let cellReuseIdentifier = "cellWish"
    let defaults = NSUserDefaults.standardUserDefaults()
    var idUser = ""
        override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = ""
        self.idUser = (defaults.objectForKey("userId") as? String)!
        service()
        table.delegate = self
        table.dataSource = self
            
        NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(WishViewController.refreshWishCount),name:"refreshWishCount", object: nil)
            
        NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(WishViewController.loadList(_:)),name:"wish", object: nil)
        let points = defaults.objectForKey("points") as! Int
        self.wishCount = defaults.objectForKey("wishCount")as!Int
        
        //Genera el boton de la derecha que contiene el corazon que abre la lista de deseos
        btn1 = Utils.loadWishListButton(btn1, wishCount: wishCount)
        btn1.addTarget(self, action: #selector(WishViewController.openWishList), forControlEvents: .TouchUpInside)
        self.navigationItem.setRightBarButtonItem(UIBarButtonItem(customView: btn1), animated: true);
        
        // Crea el view con el label de puntos y el arrow de imagen
        let myView = Utils.createPointsView(points, activateEvents: true)
        let gesture = UITapGestureRecognizer(target : self, action: #selector(WishViewController.clickOnButton))
        myView.addGestureRecognizer(gesture)
        
        self.navigationItem.titleView = myView
    }
    
    func refreshWishCount(){
        //Refresca el contador de wish al eliminar un producto de la lista
        var wish = self.wishCount
        if(wishCount>0){
        wish = wish - 1
        }
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
        let url : String = Constants.ws_services.user+"id/"+self.idUser
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
                    if(String((response)["status"] as! Int) == Constants.ws_response_code.ok)
                    {
                        user = response.objectForKey("user")! as! NSDictionary
                        let productList = user.mutableArrayValueForKey("productWishList")
                        for (_, product) in productList.enumerate()
                        {
                            let wishObject = Wish()
                            wishObject.productIdPropeties = product.objectForKey("productId") as! String
                            wishObject.productNamePropeties = product.objectForKey("productName") as! String
                            wishObject.pricePropeties = product.objectForKey("price") as! Int
                            wishObject.imageUrlListPropeties = product.objectForKey("imageUrlList") as! String
                            wishObject.pointsByPricePropeties = product.objectForKey("pointsByPrice") as! Int
                            self.wishArray.append(wishObject)
                        }
                        self.table.reloadData()
                    }
                    else
                    {
                        print(Constants.error_messages.error_wish_list)
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
    }

  
}

