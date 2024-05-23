import Foundation
import Shared

final class Koin {
    
    private var core: Koin_coreKoin?

    static let instance = Koin()

    static func start() {
        if instance.core == nil {

                let koinApp = KoinDarwinKt.doInitKoin()

                let koin = koinApp.koin
            
                instance.core = koin
        }
        if instance.core == nil {
            fatalError("Can't initialize Koin.")
        }
    }

    private init() {
    }
}


