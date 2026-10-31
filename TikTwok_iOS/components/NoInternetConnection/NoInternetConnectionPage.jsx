//IMPORT REACT UTILITIES
import { View, Text, StatusBar, StyleSheet, Dimensions } from 'react-native';
//variable
import { useBottomTabBarHeight } from '@react-navigation/bottom-tabs';
import { useHeaderHeight } from '@react-navigation/elements';


//Pagina che mostra all'utente l'assenza della connessione internet
function NoInternetConnectionPage() {
    const tabBarHeight = useBottomTabBarHeight(); 
    const headerHeight = useHeaderHeight();

    return (    
        <View style={[styles.noInternet(headerHeight, tabBarHeight)]}>
            <StatusBar barStyle='dark-content'></StatusBar>
            <Text style={{color:'white', fontSize: 20, fontWeight:'bold', color: 'red'}}>Tɯιƚ Tσƙ</Text>
            <Text>Nessuna connessione Internet 🌐</Text>
        </View>
    );
}

const styles = StyleSheet.create({
    noInternet: (headerHeight, tabBarHeight) => ({
        height: Dimensions.get('window').height - headerHeight - tabBarHeight,
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: 'white'
    }),
});

export default NoInternetConnectionPage;