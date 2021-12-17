const csvToJSON = (csv) => {
  const attributes = csv.split(',,');
  return { id: attributes[0], title: attributes[1], year: attributes[2], director: attributes[3], stars: attributes[4], review: attributes[5] };
};

export default csvToJSON;
