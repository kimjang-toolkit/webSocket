import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
#root{
  width:100vw;
  height:100vh;
  --font-size-xs: 0.75rem;  /* 12px */
  --font-size-sm: 0.875rem; /* 14px */
  --font-size-md: 1rem;     /* 16px */
  --font-size-lg: 1.125rem; /* 18px */
  --font-size-xl: 1.5rem;   /* 24px */
}
*, *::before, *::after {
    box-sizing: border-box;
    margin:0;
    padding:0;
}

html, body, div, span, applet, object, iframe,
h1, h2, h3, h4, h5, h6, p, blockquote, pre,
a, abbr, acronym, address, big, cite, code,
del, dfn, em, img, ins, kbd, q, s, samp,
small, strike, strong, sub, sup, tt, var,
b, u, i, center,
dl, dt, dd, ol, ul, li,
fieldset, form, label, legend,
table, caption, tbody, tfoot, thead, tr, th, td,
article, aside, canvas, details, embed, 
figure, figcaption, footer, header, hgroup, 
menu, nav, output, ruby, section, summary,
time, mark, audio, video {
  margin: 0;
  padding: 0;
  border: 0;
  box-sizing: border-box;
  font-size: 100%;
  font: inherit;
  vertical-align: baseline;
}

body,html {
  max-width:400px;
  margin:0 auto;
  background:#fdfdfd;
  font-family: "Helvetica", "Arial", sans-serif;
  line-height: 1.4;
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
}
button{
  background: inherit ;
  border:none;
  box-shadow:none;
  border-radius:0; 
  padding:0; 
  cursor:pointer
}
`;

export default GlobalStyle;
