//IMPORT REACT UTILITIES
import React from "react";
//context
import { ContextProvider } from './CONTEXT/ContextProvider'
//page
import Main from './components/Main';

//App principale
export default function App() {

    return (
        <ContextProvider>
            <Main></Main>
        </ContextProvider>
    );
}