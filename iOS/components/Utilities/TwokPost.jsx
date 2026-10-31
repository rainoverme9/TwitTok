//IMPORT REACT UTILITIES
import { Dimensions, StyleSheet, Text, View } from 'react-native';
//variable
import { useBottomTabBarHeight } from '@react-navigation/bottom-tabs';
import { useHeaderHeight } from '@react-navigation/elements';
//utilities
import UserInfo from './UserInfo';

//Creo un twok postato da un utente
function TwokPost(props) {

    //Ottengo il twok
    var twok = props.data.item
    
    //Analizzo gli attributi del twok
    const bgcol = "#" + twok.bgcol
    const fontcol = "#" + twok.fontcol
    const fontsize = twok.fontsize == 0 ? 20 : twok.fontsize == 1 ? 30 : twok.fontsize == 2 ? 40: 30
    const fonttype = twok.fonttype == 0 ? 'System' : twok.fonttype == 1 ? 'monospace' : twok.fonttype == 2 ? 'serif' : 'monospace'
    const halign = twok.halign == 0 ? 'flex-start' : twok.fontsize == 1 ? 'center' : twok.fontsize == 2 ? 'flex-end' : 'center'
    const valign = twok.valign == 0 ? 'flex-start' : twok.fontsize == 1 ? 'center' : twok.fontsize == 2 ? 'flex-end' : 'center'
    const lat = twok.lat ? twok.lat : null
    const lon = twok.lon ? twok.lon : null
    //varibale
    const tabBarHeight = useBottomTabBarHeight(); 
    const headerHeight = useHeaderHeight();

    return (
        <View>   
            <UserInfo uid={twok.uid} name={twok.name} pversion={twok.pversion} picture={props.pictures} screen={props.screen} navigation={props.navigation} lat={lat} lon={lon}></UserInfo>
            <View style={[styles.boxTextStyle(bgcol, halign, valign, headerHeight, tabBarHeight)]}>
                <Text style={[styles.textStyle(fontcol, fontsize, fonttype)]}>{twok.text}</Text>
            </View>       
        </View>
    );
}

const styles = StyleSheet.create({
    boxTextStyle: (bgcol, halign, valign, headerHeight, tabBarHeight) => ({
        height: Dimensions.get('window').height - headerHeight - tabBarHeight -70,
        backgroundColor: bgcol,
        alignItems: halign,
        justifyContent: valign,
        padding: 20,
    }),
    textStyle: (fontcol, fontsize, fonttype) => ({
        color: fontcol,
        fontSize: fontsize,
        fontFamily: fonttype,
    }),
});

export default TwokPost;