<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="/">
		<portfolio>
			<xsl:for-each select="investments/item[@type='stock']">
				<stock>
					<xsl:attribute name="exchange"><xsl:value-of
						select="@exch" /></xsl:attribute>
					<name>
						<xsl:value-of select="@company" />
					</name>
					<symbol>
						<xsl:value-of select="@symbol" />
					</symbol>
					<!-- to do: Add code to output the price extracted from the investments.xlm 
						file to the output stocks.xml file -->
					<price>
						<xsl:value-of select="@price" />
					</price>

				</stock>
			</xsl:for-each>
		</portfolio>
	</xsl:template>
</xsl:stylesheet>
