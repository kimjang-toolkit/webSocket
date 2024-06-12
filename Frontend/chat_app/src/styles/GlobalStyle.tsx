import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
  *, *::before, *::after {
    box-sizing: border-box;
  }
#root{
  width:100%;
  height: 100%;
  margin:0;
  padding:0;
}
  body,html {
    background: #545454;
    font-family: "Helvetica", "Arial", sans-serif;
    line-height: 1.5;
    text-rendering: optimizeLegibility;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }

  ol, ul, menu {
    list-style: none;
  }

  textarea {
    white-space: revert;
  }

  input, textarea, select {
    all: unset;
    font-family: inherit; 
`;
export default GlobalStyle;
