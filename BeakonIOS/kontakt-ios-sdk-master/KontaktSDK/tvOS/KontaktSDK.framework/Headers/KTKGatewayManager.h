//
//  KontaktSDK
//  Version: 1.2.2
//
//  Copyright © 2016 Kontakt.io. All rights reserved.
//

#import "KTKDevicesManager.h"

#pragma mark - KTKGatewayManager (Interface)
@interface KTKGatewayManager : KTKDevicesManager

- (void)startDevicesDiscoveryWithInterval:(NSTimeInterval)interval
    __attribute__((unavailable("startDevicesDiscoveryWithInterval is unavailable in KTKGatewayManager")));

@end
